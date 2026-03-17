package com.anigame.ticket_services.infrastructure.payment;

import br.com.efi.efisdk.EfiPay;
import br.com.efi.efisdk.exceptions.EfiPayException;
import com.anigame.ticket_services.infrastructure.payment.dto.CardResponse;
import com.anigame.ticket_services.domain.Customer;
import com.anigame.ticket_services.infrastructure.payment.dto.PixQRCodeResponse;
import com.anigame.ticket_services.infrastructure.payment.dto.PixResponse;
import com.anigame.ticket_services.domain.OrderEntity;
import com.anigame.ticket_services.domain.enums.ItemType;
import com.anigame.ticket_services.infrastructure.config.EfiProperties;
import com.anigame.ticket_services.infrastructure.payment.dto.creditcard.CreditCardPaymentRequest;
import com.anigame.ticket_services.infrastructure.payment.dto.pix.PixPaymentRequest;
import com.anigame.ticket_services.shared.exception.BaaIntegrationException;
import com.anigame.ticket_services.web.dto.CreditCardDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
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
    public CardResponse chargeCard(Order order, CreditCardDTO card) {
        // chamada API Efí cartão
        return null;
    }*/

    @Override
    public CardResponse chargeCard(Customer customer, OrderEntity order, CreditCardDTO card) {

        var OrderItemList = order.getItems();

        try {
            //gerar itens da cobrança
            List<com.anigame.ticket_services.infrastructure.payment.dto.OrderItem> itemList = OrderItemList.stream()
                    .map(i->{
                        return com.anigame.ticket_services.infrastructure.payment.dto.OrderItem.builder()
                            .id(i.getId())
                            .type(ItemType.the_logic_for_this_is_not_implemented)//aqui deve ser o tipo do ingresso (FULL,HALF,SOLIDARITY)
                            .unitPrice(i.getUnitPrice())
                            .quantity(i.getQuantity())
                            .build();
                    }).toList();

            JSONObject response = efiPay.call(
                    "createOneStepCharge",
                    Map.of(),
                    new JSONObject(objectMapper.writeValueAsString(
                            CreditCardPaymentRequest.from(
                                    customer.getFullName(),
                                    customer.getCpf(),
                                    customer.getPhoneNumber(),
                                    customer.getEmail(),
                                    card.installments(),
                                    card.paymentToken(),
                                    itemList
                            ))
                    )
            );

            return CardResponse.builder()
                    .chargeId(response.getJSONObject("data").getLong("charge_id"))
                    .build();

        }catch (EfiPayException e){
            log.error("Erro EFI Cartão: {}", e.getErrorDescription(), e);
            throw new BaaIntegrationException("Erro na cobrança de cartão", e);
        }
        catch (Exception e) {
            log.error("Erro inesperado cartão", e);
            throw new BaaIntegrationException("Falha interna cartão", e);
        }

    }

}

