//package com.ai.skysmart.controller;
//
//import com.ai.skysmart.service.PaymentService;
//import com.razorpay.Order;
//import com.ai.skysmart.dto.CreateOrderRequest;
//import com.ai.skysmart.dto.VerifyPaymentRequest;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import org.json.JSONObject;
//
//@RestController
//@RequestMapping("/payment")
//public class PaymentController {
//
//    @Autowired
//    private PaymentService paymentService;
//
//    // create order -> return orderId, amount & key (client uses these in checkout)
//    @PostMapping("/create-order")
//    public ResponseEntity<?> createOrder(@RequestBody CreateOrderRequest request) throws Exception {
//        Order order = paymentService.createRazorpayOrder(request.getBookingId());
//        JSONObject result = new JSONObject();
//        result.put("orderId", order.get("id"));
//        result.put("amount", order.get("amount"));
//        result.put("currency", order.get("currency"));
//        // return key id so frontend can open Razorpay checkout
//        result.put("key", paymentService.getRazorpayKey()); // add getter or expose as property
//        return ResponseEntity.ok(result.toString());
//    }
//
//    // called by client after checkout to verify signature
//    @PostMapping("/verify")
//    public ResponseEntity<String> verify(@RequestBody VerifyPaymentRequest req) {
//        boolean ok = paymentService.verifyPaymentSignature(req.getRazorpayOrderId(), req.getRazorpayPaymentId(), req.getRazorpaySignature());
//        if (ok) {
//            paymentService.completePayment(req.getRazorpayOrderId(), req.getRazorpayPaymentId(), req.getRazorpaySignature());
//            return ResponseEntity.ok("Payment verified & booking confirmed");
//        } else {
//            paymentService.failPayment(req.getRazorpayOrderId(), "Signature mismatch");
//            return ResponseEntity.status(400).body("Signature verification failed");
//        }
//    }
//
//    // webhook endpoint (optional): Razorpay will POST to this for events (payment.captured etc)
//    @PostMapping("/webhook")
//    public ResponseEntity<String> webhook(@RequestHeader("X-Razorpay-Signature") String signature,
//                                          @RequestBody String payload) {
//        // verify with webhook secret (you set this in Razorpay dashboard)
//        boolean valid = paymentService.verifyWebhookSignature(payload, signature);
//        if (!valid) return ResponseEntity.status(400).body("Invalid signature");
//
//        // parse payload, update DB for payment.captured etc.
//        paymentService.handleWebhook(payload);
//        return ResponseEntity.ok("ok");
//    }
//}
