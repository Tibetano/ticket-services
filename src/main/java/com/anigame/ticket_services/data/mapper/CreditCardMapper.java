package com.anigame.ticket_services.data.mapper;

import com.anigame.ticket_services.domain.model.old_model.charge.creditCard.CreditCardCharge;
import com.anigame.ticket_services.domain.new_impl.OrderItem;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CreditCardMapper {

    ////CreditCardTicketPaymentDTO -> CreditCardCharge
    //CreditCardPaymentDTO -> CreditCardCharge
    /*public static CreditCardCharge CCardToDomain (CreditCardTicketPaymentDTO dto) {
        CreditCardCharge charge =  new CreditCardCharge();
        charge.setTickets(
                dto.tickets().stream()
                        .map(CreditCardMapper::TicketToDomain)
                        .toList());

        charge.setPayment(
                new Payment(CCardInfoToDomain(dto.creditCard()))
        );
        return charge;
    }

    public static Ticket22 TicketToDomain (TicketDTO dto) {
        return new Ticket22(
                dto.type(),
                0,
                dto.amount()
        );
    }

    public static CreditCard CCardInfoToDomain (CreditCardDTO dto) {
        return new CreditCard(
                CustomerToDomain(
                        dto.customer()
                ),
                dto.installments(),
                dto.paymentToken()
        );
    }

    public static Customer CustomerToDomain (CustomerDTO dto) {
        return new Customer(
                dto.name(),
                dto.cpf(),
                dto.email(),
                dto.phoneNumber()
        );
    }*/

    public JSONObject toRequestBody (CreditCardCharge charge) {

        var cc = charge.getPayment().getCreditCard();
        var customer = cc.getCustomer();

        //customer
        JSONObject customerJson = new JSONObject();
        customerJson.put("name", customer.getName());
        customerJson.put("cpf", customer.getCpf());
        customerJson.put("phone_number", customer.getPhoneNumber());
        customerJson.put("email", customer.getEmail());
        customerJson.put("birth", "1990-05-04");

        /*
        //address
        JSONObject billingAddress = new JSONObject();
        billingAddress.put("street", "Av. JK");
        billingAddress.put("number", 909);
        billingAddress.put("neighborhood", "Bauxita");
        billingAddress.put("zipcode", "35400000");
        billingAddress.put("city", "Ouro Preto");
        billingAddress.put("state", "MG");
        */

        /*
        // items
        JSONArray items = new JSONArray();
        JSONObject item1 = new JSONObject();
        item1.put("name", "Item 4");
        item1.put("amount", 1);
        item1.put("value", 600);
        JSONObject item2 = new JSONObject("{\"name\":\"Item 2\", \"amount\":1, \"value\":1000}");
        items.put(item1);
        items.put(item2);
        */




        //notification URL
        JSONObject metadata = new JSONObject();
        metadata.put("notification_url", "https://requestb.in/16rpx6y1");
        metadata.put("custom_id", "id_0007");

        /*
        //discount
        JSONObject discount = new JSONObject();
        discount.put("type","currency");
        discount.put("value",599);
         */

        JSONObject creditCard = new JSONObject();
        creditCard.put("installments", cc.getInstallments());
        //creditCard.put("billing_address", billingAddress);
        creditCard.put("payment_token", cc.getPaymentToken());
        creditCard.put("customer", customerJson);
        //creditCard.put("discount", discount);

        JSONObject payment = new JSONObject();
        payment.put("credit_card", creditCard);

        JSONObject body = new JSONObject();
        body.put("payment", payment);
        body.put("items", buildItems(charge));
        body.put("metadata", metadata);

        return body;
    }

    private JSONArray buildItems(CreditCardCharge charge) {
        JSONArray tickets = new JSONArray();
        charge.getTickets().forEach(t -> {
            if (t.getAmount() > 0) {
                JSONObject ticket = new JSONObject();
                ticket.put("name", t.getName());
                ticket.put("amount", t.getAmount());
                ticket.put("value", t.getValue());
                tickets.put(ticket);
            }
        });
        return tickets;
    }

    public JSONObject generateRequestBody (String customerName, String customerCPF, String customerPhoneNumber,
                                           String customerEmail, Integer installments, String paymentToken, List<OrderItem> items
    ) {

        //customer
        JSONObject customerJson = new JSONObject();
        customerJson.put("name", customerName);
        customerJson.put("cpf", customerCPF);
        customerJson.put("phone_number", customerPhoneNumber);
        customerJson.put("email", customerEmail);

        //notification URL
        JSONObject metadata = new JSONObject();
        metadata.put("notification_url", "https://requestb.in/16rpx6y1");
        metadata.put("custom_id", "id_0007");

        JSONObject creditCard = new JSONObject();
        creditCard.put("installments", installments);
        creditCard.put("payment_token", paymentToken);
        creditCard.put("customer", customerJson);

        JSONObject payment = new JSONObject();
        payment.put("credit_card", creditCard);

        JSONObject body = new JSONObject();
        body.put("payment", payment);
        body.put("items", buildItemList(items));
        body.put("metadata", metadata);

        return body;
    }

    private JSONArray buildItemList(List<OrderItem> items) {
        JSONArray tickets = new JSONArray();
        items.forEach(t -> {
            if (t.getQuantity() > 0) {
                JSONObject ticket = new JSONObject();
                ticket.put("name", t.getType());
                ticket.put("amount", t.getQuantity());
                ticket.put("value", t.getUnitPrice());
                tickets.put(ticket);
            }
        });
        return tickets;
    }
}
