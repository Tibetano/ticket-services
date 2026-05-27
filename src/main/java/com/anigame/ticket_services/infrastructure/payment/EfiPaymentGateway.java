package com.anigame.ticket_services.infrastructure.payment;

import com.anigame.ticket_services.domain.Customer;
import com.anigame.ticket_services.domain.order.OrderEntity;
import com.anigame.ticket_services.infrastructure.clients.EfiPayClient;
import com.anigame.ticket_services.infrastructure.clients.EfiPayPixClient;
import com.anigame.ticket_services.infrastructure.clients.dto.EfiPixQRCodeResponse;
import com.anigame.ticket_services.infrastructure.clients.dto.PixChargeRequest;
import com.anigame.ticket_services.infrastructure.clients.dto.PixChargeResponse;
import com.anigame.ticket_services.infrastructure.config.EfiProperties;
import com.anigame.ticket_services.infrastructure.payment.dto.CardResponse;
import com.anigame.ticket_services.infrastructure.payment.dto.OrderItem;
import com.anigame.ticket_services.infrastructure.payment.dto.creditcard.CreditCardPaymentRequest;
import com.anigame.ticket_services.shared.exception.exceptions.BaaIntegrationException;
import com.anigame.ticket_services.usecase.webhook.dto.charge.DataDTO;
import com.anigame.ticket_services.usecase.webhook.dto.charge.EfiTransactionStatusResponseDTO;
import com.anigame.ticket_services.web.dto.CreditCardDTO;
import com.anigame.ticket_services.web.dto.response.PixPaymentDetailsResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class EfiPaymentGateway implements PaymentGateway {

    private final EfiProperties props;

    private final EfiPayPixClient pixClient;
    private final EfiPayClient creditCardClient;

    @Override
    public PixChargeResponse generatePix(Customer customer, OrderEntity order, BigDecimal percentage) {

        try {
            PixChargeRequest request =
                    new PixChargeRequest();

            PixChargeRequest.Calendario calendario =
                    new PixChargeRequest.Calendario();

            calendario.setExpiracao(props.getQRCodeExpirationTime());

            PixChargeRequest.Devedor devedor =
                    new PixChargeRequest.Devedor();

            devedor.setCpf(customer.getCpf());
            devedor.setNome(customer.getFullName());

            PixChargeRequest.Valor valor =
                    new PixChargeRequest.Valor();


            BigDecimal fee = BigDecimal.valueOf(order.getTotalAmount())
                    .multiply(percentage)
                    .divide(BigDecimal.valueOf(100),10, RoundingMode.HALF_UP);
            String taxedUnitPrice = BigDecimal.valueOf(order.getTotalAmount())
                    .add(fee)
                    .setScale(0, RoundingMode.HALF_UP)
                    .scaleByPowerOfTen(-2)
                    .toPlainString();

            valor.setOriginal(taxedUnitPrice);
            //valor.setOriginal(BigDecimal.valueOf(order.getTotalAmount()).scaleByPowerOfTen(-2).toPlainString());

            request.setCalendario(calendario);
            request.setDevedor(devedor);
            request.setValor(valor);

            request.setChave(props.getPixKey());

            request.setSolicitacaoPagador(
                    "Pagamento teste"
            );

            String txId = UUID.randomUUID()
                    .toString()
                    .replace("-", "")
                    .substring(0, 26);

            return pixClient.createCharge(
                    txId,
                    request
            );
        } catch(Exception ex) {
            log.error("Erro inesperado ao gerar PIX", ex);
            throw new BaaIntegrationException("Falha interna ao gerar PIX", ex);
        }
    }

    @Override
    public PixPaymentDetailsResponse generatePixQRCode(Long locId) {
        try{
            EfiPixQRCodeResponse response = pixClient.generateChargeQRCode(locId);
            return new PixPaymentDetailsResponse(
                    response.qrCodeImage(),
                    response.qrCode(),
                    response.visualizationLink()
            );
        } catch(Exception ex) {
            log.error("Erro inesperado ao gerar PIX", ex);
            throw new BaaIntegrationException("Falha interna ao gerar PIX", ex);
        }
    }

    @Override
    public CardResponse generateChargeCard(Customer customer, OrderEntity order, CreditCardDTO card, BigDecimal percentage) {

        try {
            //gerar itens da cobrança
            List<OrderItem> itemList = order.getItems().stream()
                    .map(i->{
                        //gerar a taxa sobre o preço unitário e somar essa taxa ao valor antes de chamar o efí
                        BigDecimal fee = BigDecimal.valueOf(i.getUnitPrice())
                                .multiply(percentage)
                                .divide(BigDecimal.valueOf(100),10, RoundingMode.HALF_UP);
                        Integer taxedUnitPrice = BigDecimal.valueOf(i.getUnitPrice())
                                .add(fee)
                                .setScale(0, RoundingMode.HALF_UP)
                                .intValue();

                        return OrderItem.builder()
                                .id(i.getId())
                                //.type(ItemType.the_logic_for_this_is_not_implemented)//aqui deve ser o tipo do ingresso (FULL,HALF,SOLIDARITY)
                                .type(i.getTicketBatchTypeEntity().getTicketType())//aqui deve ser o tipo do ingresso (FULL,HALF,SOLIDARITY)
                                .unitPrice(taxedUnitPrice)
                                .quantity(i.getQuantity())
                                .build();
                    }).toList();

            var body = CreditCardPaymentRequest.from(
                            customer.getFullName(),
                            customer.getCpf(),
                            customer.getPhoneNumber(),
                            customer.getEmail(),
                            card.installments(),
                            card.paymentToken(),
                            props.getChargeNotificationUrl(),
                            itemList
            );

            return creditCardClient.createCharge(body);

        } catch (Exception e) {

            System.out.println(e.getMessage());

            log.error("Erro inesperado cartão", e);
            throw new BaaIntegrationException("Falha interna cartão", e);
        }
    }

    @Override
    public DataDTO getPaymentStatus(String notificationToken) {

        try {

            EfiTransactionStatusResponseDTO notificationResponse = creditCardClient.getNotification(notificationToken);

            return notificationResponse.data()
                    .stream()
                    .max(Comparator.comparing(DataDTO::id))
                    .orElse(null);

        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

}

