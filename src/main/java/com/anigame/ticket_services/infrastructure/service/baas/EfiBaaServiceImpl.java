package com.anigame.ticket_services.infrastructure.service.baas;

import br.com.efi.efisdk.EfiPay;
import br.com.efi.efisdk.exceptions.EfiPayException;
import com.anigame.ticket_services.data.mapper.CreditCardMapper;
import com.anigame.ticket_services.data.mapper.PixMapper;
import com.anigame.ticket_services.domain.model.old_model.charge.pix.PixQRCode;
import com.anigame.ticket_services.domain.model.old_model.charge.creditCard.CreditCardCharge;
import com.anigame.ticket_services.domain.model.old_model.database.Payment;
import com.anigame.ticket_services.domain.model.old_model.database.enumerate.PaymentMethodEnum;
import com.anigame.ticket_services.domain.model.old_model.database.enumerate.PaymentStatusEnum;
import com.anigame.ticket_services.domain.persistence.PaymentRepository;
import com.anigame.ticket_services.domain.service.BaaService;
import com.anigame.ticket_services.infrastructure.configuration.EfiProperties;
import com.anigame.ticket_services.infrastructure.exception.BaaIntegrationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class EfiBaaServiceImpl implements BaaService {

    private final EfiPay efiPay;
    private final PixMapper pixMapper;
    private final CreditCardMapper creditCardMapper;
    private final EfiProperties props;

    private final PaymentRepository paymentRepository;

    @Override
    public PixQRCode pixGenerateCharge(String fullName, String cpf, UUID orderId, Integer totalAmount) throws URISyntaxException {

        String formattedTotalAmount = BigDecimal
                .valueOf(totalAmount, 2) // já divide por 10^2
                .toPlainString();
        //boolean isValid = formatted.matches("\\d{1,10}\\.\\d{2}");//uma forma de verificar a formatação do valor


        try {
            JSONObject pixChargeInfo = efiPay.call(
                    "pixCreateImmediateCharge",
                    Map.of(),
                    pixMapper.toPixBody(
                            fullName,
                            cpf,
                            props.getQRCodeExpirationTime(),
                            formattedTotalAmount,
                            props.getPixKey()
                    )
            );

            String locId = String.valueOf(pixChargeInfo.getJSONObject("loc").getInt("id"));

            JSONObject pixChargeQRCode = efiPay.call(
                    "pixGenerateQRCode",
                    new HashMap<>(Map.of("id",locId)),
                    new JSONObject());


            //PEGAR AS INFORMAÇÕES RETORNADAS PELAS DUAS CHAMADAS PRO EFI E SALVAR NA TABELA PAYMENTS

            var paymentInfo = Payment.builder()
                    .orderId(orderId)
                    .provider("Efí-Bank")
                    .method(PaymentMethodEnum.PIX)
                    .providerChargeId("Not-used")
                    .providerTxId(pixChargeInfo.getString("txid"))//verificar se esse nome de atributo é o real retornado
                    .providerStatus("lol")
                    .amount(totalAmount)
                    .status(PaymentStatusEnum.PENDING)
                    .idempotencyKey("lol")//gerar a lógica da idenpotence key
                    .failureReason("lol")
                    .rawResponse("{\"message\":\"lol\"}")
                    .expiresAt(LocalDateTime.now())
                    .confirmedAt(LocalDateTime.now())
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .build();

            //paymentRepository.save(paymentInfo);

            return new PixQRCode(
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

    @Override
    public void generateCreditCardCharge(CreditCardCharge creditCardCharge) {
        try {
            creditCardCharge.getTickets().forEach(t -> t.setValue(
                            t.getName().equals("Inteiro") ?
                                    (int) (Double.parseDouble(props.getOriginalTicketPrice()) * 100) :
                                    (int) (Double.parseDouble(props.getOriginalTicketPrice()) * 100) / 2
                    )
            );

            JSONObject response = efiPay.call(
                    "createOneStepCharge",
                    Map.of(),
                    creditCardMapper.toRequestBody(creditCardCharge)
            );
            log.info("Cobrança cartão criada: {}", response);
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
