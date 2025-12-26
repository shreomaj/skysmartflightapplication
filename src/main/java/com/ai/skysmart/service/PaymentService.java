//package com.ai.skysmart.service;
//
//import com.ai.skysmart.entity.Booking;
//import com.ai.skysmart.entity.Payment;
//import com.ai.skysmart.entity.PaymentStatus;
//import com.ai.skysmart.repository.BookingRepository;
//import com.ai.skysmart.repository.PaymentRepository;
//import com.razorpay.Order;
//import com.razorpay.RazorpayClient;
//import org.json.JSONObject;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//
//import javax.crypto.Mac;
//import javax.crypto.spec.SecretKeySpec;
//
//@Service
//public class PaymentService {
//
//    @Autowired
//    private BookingRepository bookingRepo;
//
//    @Autowired
//    private PaymentRepository paymentRepo;
//
//    @Autowired
//    private TicketService ticketService;
//
//    @Autowired
//    private RazorpayClient razorpayClient;
//
//    @Value("${razorpay.key_id}")
//    private String razorpayKey;
//
//    @Value("${razorpay.key_secret}")
//    private String razorpaySecret;
//
//
//    // ================= CREATE ORDER =======================
//    public Order createRazorpayOrder(String bookingId) {
//        Booking booking = bookingRepo.findById(bookingId)
//                .orElseThrow(() -> new RuntimeException("Booking ID not found"));
//
//        int amountPaise = (int) Math.round(booking.getTotalAmount() * 100);
//
//        JSONObject orderRequest = new JSONObject();
//        orderRequest.put("amount", amountPaise);
//        orderRequest.put("currency", "INR");
//        orderRequest.put("receipt", "rcpt_" + booking.getBookingId());
//        orderRequest.put("payment_capture", 1);
//
//        try {
//            Order order = razorpayClient.Orders.create(orderRequest);
//
//            Payment payment = new Payment();
//            payment.setBooking(booking);
//            payment.setStatus(PaymentStatus.PENDING);
//            payment.setAmount(booking.getTotalAmount());
//            payment.setPaymentMethod("RAZORPAY");
//            payment.setRazorpayOrderId(order.get("id"));
//            paymentRepo.save(payment);
//
//            return order;
//
//        } catch (Exception e) {
//            throw new RuntimeException("Failed to create Razorpay Order");
//        }
//    }
//
//
//    // ================= GET PUBLIC KEY ====================
//    public String getRazorpayKey() {
//        return razorpayKey;
//    }
//
//
//    // ================= SIGNATURE VERIFICATION =============
//    public boolean verifyPaymentSignature(String orderId, String paymentId, String razorpaySignature) {
//        try {
//            String payload = orderId + "|" + paymentId;
//
//            Mac mac = Mac.getInstance("HmacSHA256");
//            SecretKeySpec keySpec = new SecretKeySpec(razorpaySecret.getBytes(), "HmacSHA256");
//            mac.init(keySpec);
//
//            byte[] hash = mac.doFinal(payload.getBytes());
//
//            String generatedSignature = bytesToHex(hash);
//
//            return generatedSignature.equalsIgnoreCase(razorpaySignature);
//
//        } catch (Exception e) {
//            return false;
//        }
//    }
//
//    private String bytesToHex(byte[] bytes) {
//        StringBuilder sb = new StringBuilder();
//        for (byte b : bytes)
//            sb.append(String.format("%02x", b));
//        return sb.toString();
//    }
//
//
//    // ================ PAYMENT SUCCESS ====================
//    public void completePayment(String orderId, String paymentId, String signature) {
//        Payment payment = paymentRepo.findByRazorpayOrderId(orderId);
//
//        if (payment == null)
//            throw new RuntimeException("Payment not found");
//
//        payment.setRazorpayPaymentId(paymentId);
//        payment.setRazorpaySignature(signature);
//        payment.setStatus(PaymentStatus.SUCESS);
//        paymentRepo.save(payment);
//
//        Booking booking = payment.getBooking();
//        booking.setStatus("CONFIRMED");
//        bookingRepo.save(booking);
//
//        ticketService.generateTicketsForBooking(booking);
//    }
//
//
//    // ================ PAYMENT FAILURE ====================
//    public void failPayment(String orderId, String reason) {
//        Payment payment = paymentRepo.findByRazorpayOrderId(orderId);
//        if (payment == null) return;
//
//        payment.setStatus(PaymentStatus.FAILED);
//        paymentRepo.save(payment);
//
//        Booking booking = payment.getBooking();
//        booking.setStatus("PAYMENT_FAILED");
//        bookingRepo.save(booking);
//    }
//
//
//    // ================= WEBHOOK SIGNATURE ==================
//    public boolean verifyWebhookSignature(String payload, String signature) {
//        try {
//            Mac mac = Mac.getInstance("HmacSHA256");
//            SecretKeySpec secretKey = new SecretKeySpec(razorpaySecret.getBytes(), "HmacSHA256");
//            mac.init(secretKey);
//
//            byte[] hash = mac.doFinal(payload.getBytes());
//            String expected = bytesToHex(hash);
//
//            return expected.equalsIgnoreCase(signature);
//
//        } catch (Exception e) {
//            return false;
//        }
//    }
//
//
//    // ================= WEBHOOK HANDLER ====================
//    public void handleWebhook(String payload) {
//        JSONObject json = new JSONObject(payload);
//        String event = json.getString("event");
//
//        if (event.equals("payment.captured")) {
//            String orderId = json.getJSONObject("payload")
//                    .getJSONObject("payment")
//                    .getJSONObject("entity")
//                    .getString("order_id");
//
//            Payment payment = paymentRepo.findByRazorpayOrderId(orderId);
//            if (payment != null) {
//                payment.setStatus(PaymentStatus.SUCESS);
//                paymentRepo.save(payment);
//
//                Booking booking = payment.getBooking();
//                booking.setStatus("CONFIRMED");
//                bookingRepo.save(booking);
//
//                ticketService.generateTicketsForBooking(booking);
//            }
//        }
//    }
//}
