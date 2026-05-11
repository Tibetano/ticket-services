package com.anigame.ticket_services.infrastructure.payment;

import br.com.efi.efisdk.EfiPay;
import br.com.efi.efisdk.exceptions.EfiPayException;
import com.anigame.ticket_services.domain.Customer;
import com.anigame.ticket_services.domain.order.OrderEntity;
import com.anigame.ticket_services.infrastructure.config.EfiProperties;
import com.anigame.ticket_services.infrastructure.payment.dto.CardResponse;
import com.anigame.ticket_services.infrastructure.payment.dto.OrderItem;
import com.anigame.ticket_services.infrastructure.payment.dto.PixQRCodeResponse;
import com.anigame.ticket_services.infrastructure.payment.dto.PixResponse;
import com.anigame.ticket_services.infrastructure.payment.dto.creditcard.CreditCardPaymentRequest;
import com.anigame.ticket_services.infrastructure.payment.dto.pix.PixPaymentRequest;
import com.anigame.ticket_services.shared.exception.exceptions.BaaIntegrationException;
import com.anigame.ticket_services.usecase.webhook.dto.charge.DataDTO;
import com.anigame.ticket_services.usecase.webhook.dto.charge.EfíTransactionStatusResponseDTO;
import com.anigame.ticket_services.web.dto.CreditCardDTO;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class EfiPaymentGateway implements PaymentGateway {

    private final EfiPay efiPay;
    private final EfiProperties props;
    private final ObjectMapper objectMapper;



    /*@Override
    public PixResponse generatePix(Order order) {
        // chamada API Efí
        return null;
    }*/

    @Override
    public PixResponse generatePix(Customer customer, OrderEntity order) {
        // chamada API Efí

        try {
            JSONObject pixChargeInfo = efiPay.call(
                    "pixCreateImmediateCharge",
                    Map.of(),
                    new JSONObject(objectMapper.writeValueAsString(
                            PixPaymentRequest.from(
                                    customer.getFullName(),
                                    customer.getCpf(),
                                    props.getQRCodeExpirationTime(),
                                    BigDecimal.valueOf(order.getTotalAmount()).scaleByPowerOfTen(-2).toPlainString(),
                                    props.getPixKey()
                            ))
                    )
            );

            return PixResponse.builder()
                    .txId(pixChargeInfo.getString("txid"))
                    .locId(String.valueOf(pixChargeInfo.getJSONObject("loc").getInt("id")))
                    .build();

        } catch(EfiPayException e) {
            log.error("Erro EFI PIX code={}, error={}", e.getCode(), e.getError(), e);
            throw new BaaIntegrationException("Erro na integração PIX", e);
        } catch(Exception ex) {
            log.error("Erro inesperado ao gerar PIX", ex);
            throw new BaaIntegrationException("Falha interna ao gerar PIX", ex);
        }

    }

    @Override
    public PixQRCodeResponse generatePixQRCode(String locId) {

        try{
        JSONObject pixChargeQRCode = efiPay.call(
                "pixGenerateQRCode",
                new HashMap<>(Map.of("id",locId)),
                new JSONObject());

        return new PixQRCodeResponse(
                pixChargeQRCode.getString("imagemQrcode"),
                pixChargeQRCode.getString("qrcode"),
                pixChargeQRCode.getString("linkVisualizacao")
        );
        } catch(EfiPayException e) {
            log.error("Erro EFI PIX code={}, error={}", e.getCode(), e.getError(), e);
            throw new BaaIntegrationException("Erro na integração PIX", e);
        } catch(Exception ex) {
            log.error("Erro inesperado ao gerar PIX", ex);
            throw new BaaIntegrationException("Falha interna ao gerar PIX", ex);
        }

    }

    /*@Override
    public CardResponse generateChargeCard(Order order, CreditCardDTO card) {
        // chamada API Efí cartão
        return null;
    }*/

    @Override
    public CardResponse generateChargeCard(Customer customer, OrderEntity order, CreditCardDTO card) {

        var OrderItemList = order.getItems();

        try {
            //gerar itens da cobrança
           List<OrderItem> itemList = OrderItemList.stream()
                    .map(i->{
                        return OrderItem.builder()
                                .id(i.getId())
                                //.type(ItemType.the_logic_for_this_is_not_implemented)//aqui deve ser o tipo do ingresso (FULL,HALF,SOLIDARITY)
                                .type(i.getTicketBatchTypeEntity().getTicketType())//aqui deve ser o tipo do ingresso (FULL,HALF,SOLIDARITY)
                                .unitPrice(i.getUnitPrice())
                                .quantity(i.getQuantity())
                                .build();
                    }).toList();

            var body = new JSONObject(objectMapper.writeValueAsString(
                    CreditCardPaymentRequest.from(
                            customer.getFullName(),
                            customer.getCpf(),
                            customer.getPhoneNumber(),
                            customer.getEmail(),
                            card.installments(),
                            card.paymentToken(),
                            props.getChargeNotificationUrl(),
                            itemList
                    ))
            );

            log.info("JSON ENVIADO EFI:\n{}", body.toString(2));

            JSONObject response = efiPay.call(
                    "createOneStepCharge",
                    Map.of(),
                    body
            );

            System.out.println("Resposta do efí: ");
            System.out.println(response);

            return CardResponse.builder()
                    .chargeId(response.getJSONObject("data").getLong("charge_id"))
                    .build();

        } catch (EfiPayException e){

            System.out.println(e.getCode());
            System.out.println(e.getError());
            System.out.println(e.getErrorDescription());

            log.error("Erro EFI Cartão: {}", e.getErrorDescription(), e);

            throw new BaaIntegrationException("Erro na cobrança de cartão", e);
        } catch (Exception e) {

            System.out.println(e.getMessage());

            log.error("Erro inesperado cartão", e);
            throw new BaaIntegrationException("Falha interna cartão", e);
        }

    }

    @Override
    public DataDTO getPaymentStatus(String notificationToken) {
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        //chamar o efí e buscar o resultado da conta do token
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("token", notificationToken);

        try {
            JSONObject response = efiPay.call("getNotification", params, new JSONObject());
            EfíTransactionStatusResponseDTO res = objectMapper.readValue(response.toString(), EfíTransactionStatusResponseDTO.class);
            //O objeto retornado pelo efí trás o histírico de mudança de status da cobrança mas na linha abaixo está sendo retornada apenas a última mudança de status.
            return res.data()
                    .stream()
                    .max(Comparator.comparing(DataDTO::id))
                    .orElse(null);
        } catch (EfiPayException e) {
            System.out.println(e.getCode());
            System.out.println(e.getError());
            System.out.println(e.getErrorDescription());
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

}

