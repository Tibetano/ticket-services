package com.anigame.ticket_services.infrastructure.service.baas;

import br.com.efi.efisdk.EfiPay;
import br.com.efi.efisdk.exceptions.EfiPayException;
import com.anigame.ticket_services.data.mapper.CreditCardMapper;
import com.anigame.ticket_services.data.mapper.PixMapper;
import com.anigame.ticket_services.domain.model.pix.PixQRCode;
import com.anigame.ticket_services.domain.model.creditCard.CreditCardCharge;
import com.anigame.ticket_services.domain.service.BaaService;
import com.anigame.ticket_services.infrastructure.configuration.EfiProperties;
import com.anigame.ticket_services.infrastructure.exception.BaaIntegrationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.net.URISyntaxException;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class EfiBaaServiceImpl implements BaaService {

    private final EfiPay efiPay;
    private final PixMapper pixMapper;
    private final CreditCardMapper creditCardMapper;
    private final EfiProperties props;

    @Override
    public PixQRCode generatePixCharge(String fullName, String cpf) throws URISyntaxException {
        try {
            JSONObject pixChargeInfo = efiPay.call(
                    "pixCreateImmediateCharge",
                    Map.of(),
                    pixMapper.toPixBody(fullName, cpf));

            String locId = String.valueOf(pixChargeInfo.getJSONObject("loc").getInt("id"));

            JSONObject pixChargeQRCode = efiPay.call(
                    "pixGenerateQRCode",
                    Map.of("id",locId),
                    new JSONObject());

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
