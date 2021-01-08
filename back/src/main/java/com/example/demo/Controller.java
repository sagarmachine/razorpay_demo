package com.example.demo;


import com.example.demo.model.*;
import com.google.gson.Gson;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import com.razorpay.Refund;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.math.RoundingMode;

@RestController
@CrossOrigin(value = "http://localhost:3000")
public class Controller {


    private static Gson gson = new Gson();

    private static final String SECRET_ID = "rzp_test_sefvNlSwvIdbfc";
    private static final String SECRET_KEY = "3yp2eI38Gt0OVBAx6NYTmYtG";
    RazorpayClient client=null;

            Controller() throws RazorpayException {
                client=new RazorpayClient(SECRET_ID,SECRET_KEY);

    }

    @PostMapping(value = "/test/{parameter}")
    public String test(@Valid  @RequestBody TestModel testModel, @PathVariable("parameter")String parameter){

                return "Success";
    }




    @RequestMapping(value = "/capture/{pmt}/{amt}")
    public Object capture(@PathVariable("pmt")String pmtID,@PathVariable ("amt")String amt ){


        System.out.println(amt);
        JSONObject options = new JSONObject();
        options.put("amount", convertRupeeToPaise(amt));
        options.put("currency", "INR");
        try {
            return client.Payments.capture(pmtID, options);
        } catch (RazorpayException e) {
            System.out.println("Captured "+e.getMessage());
        }

return pmtID;
    }


    @RequestMapping(value = "/order/{amt}")
    public ResponseEntity<?> order(@PathVariable("amt")String amt){
        try {

            /**
             * creating an order in RazorPay.
             * new order will have order id. you can get this order id by calling  order.get("id")
             */
            Order order = createRazorPayOrder( amt);
            RazorPay razorPay = getRazorPay((String)order.get("id"), amt);

            return new ResponseEntity<String>(gson.toJson(getResponse(razorPay, 200)),
                    HttpStatus.OK);
        } catch (RazorpayException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<String>(gson.toJson(getResponse(new RazorPay(), 500)),
                HttpStatus.EXPECTATION_FAILED);

    }


    @RequestMapping("/refund/{amt}")
    public Object refund(@RequestBody String pmt, @PathVariable("amt") String amt) throws RazorpayException {
        JSONObject refundRequest = new JSONObject();
        refundRequest.put("amount",convertRupeeToPaise(amt));
        refundRequest.put("payment_id", pmt.substring(0,pmt.length()-1));
        Refund refund = client.Payments.refund(refundRequest);

        return refund;
    }

    @RequestMapping("/refund")
    public Object refund(@RequestBody String pmt) throws RazorpayException {

        System.out.println(pmt);
        JSONObject refundRequest = new JSONObject();
        refundRequest.put("payment_id", pmt.substring(0,pmt.length()-1));
        Refund refund = client.Payments.refund(refundRequest);

        return refund;
    }

    private Response getResponse(RazorPay  razorPay, int statusCode) {
        Response response = new Response();
        response.setStatusCode(statusCode);
        response.setRazorPay(razorPay);
        return response;
    }

    private Order createRazorPayOrder(String amount) throws RazorpayException {


        System.out.println(convertRupeeToPaise(amount));

        JSONObject options = new JSONObject();
        options.put("amount",convertRupeeToPaise(amount) );
        options.put("currency", "INR");
        options.put("receipt", "txn_123456");
        options.put("payment_capture", 1); // You can enable this if you want to do Auto Capture.
        return client.Orders.create(options);
    }


    private RazorPay getRazorPay(String orderId, String  amount) {
        RazorPay razorPay = new RazorPay();
        razorPay.setApplicationFee(convertRupeeToPaise(amount));
      //  razorPay.setCustomerName(customer.getCustomerName());
      //  razorPay.setCustomerEmail(customer.getEmail());
       // razorPay.setMerchantName("Test");
       // razorPay.setPurchaseDescription("TEST PURCHASES");
        razorPay.setRazorpayOrderId(orderId);
        razorPay.setSecretKey(SECRET_ID);
      //  razorPay.setImageURL("/logo");
      //  razorPay.setTheme("#F37254");
      //  razorPay.setNotes("notes"+orderId);

        return razorPay;
    }
    private String convertRupeeToPaise(String paise) {
        BigDecimal b = new BigDecimal(paise);
        BigDecimal value = b.multiply(new BigDecimal("100"));
        return value.setScale(0, RoundingMode.UP).toString();

    }
}
