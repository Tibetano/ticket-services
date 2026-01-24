package com.anigame.ticket_services.infrastructure.service.baas;

import br.com.efi.efisdk.EfiPay;
import br.com.efi.efisdk.exceptions.EfiPayException;
import com.anigame.ticket_services.domain.model.PixQRCode;
import com.anigame.ticket_services.domain.model.creditCard.CreditCardCharge;
import com.anigame.ticket_services.domain.service.BaaService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.net.URISyntaxException;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;

@Service
public class EfiBaaServiceImpl implements BaaService {

    @Override
    public PixQRCode generatePixCharge(String fullName, String cpf) throws URISyntaxException {

        URL certUrl = getClass().getClassLoader()
                .getResource("certs/homologacao-686823-ani-teste-cert-hom.p12");

        System.out.println("CertURL: " + certUrl);

        if (certUrl == null) {
            throw new RuntimeException("Certificado não encontrado em resources/certs");
        }

        String certPath = new java.io.File(certUrl.toURI()).getAbsolutePath();

        JSONObject options = new JSONObject();
        options.put("client_id","Client_Id_4d5a212bae6ba3534c9c60fc1004233494d343d6");
        options.put("client_secret","Client_Secret_578f36b9cee8b6c8aaade1789449349b1c1bda12");
        options.put("certificate", certPath);
        options.put("sandbox",true);

        JSONObject body = new JSONObject();
        body.put("calendario", new JSONObject().put("expiracao", 3600));
        body.put("devedor", new JSONObject().put("cpf", "12345678909").put("nome", "Francisco da Silva"));
        body.put("valor", new JSONObject().put("original", "0.01"));
        body.put("chave", "71cdf9ba-c695-4e3c-b010-abb521a3f1be");
        body.put("solicitacaoPagador", "Serviço realizado.");

        try {
            EfiPay efi = new EfiPay(options);

            JSONObject pixChargeInfo = efi.call("pixCreateImmediateCharge", new HashMap<String,String>(), body);

            HashMap<String, String> params = new HashMap<String,String>();
            params.put("id", String.valueOf(pixChargeInfo.getJSONObject("loc").getInt("id")));

            JSONObject pixChargeQRCode = efi.call("pixGenerateQRCode", params, new JSONObject());

            return new PixQRCode(
                    pixChargeQRCode.getString("imagemQrcode"),
                    pixChargeQRCode.getString("qrcode"),
                    pixChargeQRCode.getString("linkVisualizacao")
            );

        } catch(EfiPayException e) {
            System.out.println("erro1");
            System.out.println(e.getCode());
            System.out.println(e.getError());
            System.out.println(e.getMessage());
            System.out.println(e.getErrorDescription());
            /* EfiPay's api errors will come here */
        } catch(Exception ex) {
            System.out.println("erro2");
            System.out.println(ex.getMessage());
            System.out.println(Arrays.toString(ex.getStackTrace()));
            /* Other errors will come here */
        }

        throw new RuntimeException("Erro ao gerar cobrança Pix");
    }

    @Override
    public void generateCreditCardCharge(CreditCardCharge creditCardCharge) {

        /* *********  Set credentials parameters ******** */
        //Credentials credentials = new Credentials();
        JSONObject options = new JSONObject();
        options.put("client_id", "Client_Id_4d5a212bae6ba3534c9c60fc1004233494d343d6");
        options.put("client_secret", "Client_Secret_578f36b9cee8b6c8aaade1789449349b1c1bda12");
        options.put("sandbox", true);
        /* ************************************************* */
        String paymentToken = creditCardCharge.getPayment().getCreditCard().getPaymentToken();

        // items
        JSONArray items = new JSONArray();
        JSONObject item1 = new JSONObject();
        item1.put("name", "Item 4");
        item1.put("amount", 1);
        item1.put("value", 600);
        JSONObject item2 = new JSONObject("{\"name\":\"Item 2\", \"amount\":1, \"value\":1000}");
        items.put(item1);
        items.put(item2);

        //customer
        JSONObject customer = new JSONObject();
        customer.put("name", creditCardCharge.getPayment().getCreditCard().getCustomer().getName());
        customer.put("cpf", "94271564656");
        customer.put("phone_number", "5144916523");
        customer.put("email", "client_email@server.com.br");
        customer.put("birth", "1990-05-04");

        //address
        JSONObject billingAddress = new JSONObject();
        billingAddress.put("street", "Av. JK");
        billingAddress.put("number", 909);
        billingAddress.put("neighborhood", "Bauxita");
        billingAddress.put("zipcode", "35400000");
        billingAddress.put("city", "Ouro Preto");
        billingAddress.put("state", "MG");

        //notification URL
        JSONObject metadata = new JSONObject();
        metadata.put("notification_url", "https://requestb.in/16rpx6y1");
        metadata.put("custom_id", "id_0007");

        //discount
        JSONObject discount = new JSONObject();
        discount.put("type","currency");
        discount.put("value",599);

        JSONObject creditCard = new JSONObject();
        creditCard.put("installments", 1);
        creditCard.put("billing_address", billingAddress);
        creditCard.put("payment_token", paymentToken);
        creditCard.put("customer", customer);
        creditCard.put("discount", discount);

        JSONObject payment = new JSONObject();
        payment.put("credit_card", creditCard);

        JSONObject body = new JSONObject();
        body.put("payment", payment);
        body.put("items", items);
        body.put("metadata", metadata);

        try {
            EfiPay efi = new EfiPay(options);
            JSONObject response = efi.call("createOneStepCharge", new HashMap<String,String>(), body);
            System.out.println(response);
        }catch (EfiPayException e){
            System.out.println(e.getCode());
            System.out.println(e.getError());
            System.out.println(e.getErrorDescription());
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}
