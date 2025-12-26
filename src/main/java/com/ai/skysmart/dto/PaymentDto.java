package com.ai.skysmart.dto;
//We won’t store card details — just validate them and proceed.
public class PaymentDto {
        private String cardNumber;
        private String cvv;
        private String expiry;
        private String holderName;

        public PaymentDto(String cardNumber, String cvv, String expiry, String holderName) {
            this.cardNumber = cardNumber;
            this.cvv = cvv;
            this.expiry = expiry;
            this.holderName = holderName;
        }

        public PaymentDto() {
        }

        public String getCardNumber() {
            return cardNumber;
        }

        public void setCardNumber(String cardNumber) {
            this.cardNumber = cardNumber;
        }

        public String getCvv() {
            return cvv;
        }

        public void setCvv(String cvv) {
            this.cvv = cvv;
        }

        public String getExpiry() {
            return expiry;
        }

        public void setExpiry(String expiry) {
            this.expiry = expiry;
        }

        public String getHolderName() {
            return holderName;
        }

        public void setHolderName(String holderName) {
            this.holderName = holderName;
        }
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long paymentId;
//
//    private Double amount;
//
//    @Enumerated(EnumType.STRING)
//    private PaymentStatus status;
//
//    private String paymentMethod; // CARD, UPI, etc.
//
//    private String razorpayOrderId;
//    private String razorpayPaymentId;
//    private String razorpaySignature;
//
//    @OneToOne
//    @JoinColumn(name="booking_id")
//    private Booking booking;
//
//    private LocalDateTime createdAt = LocalDateTime.now();
//
//    public Long getPaymentId() {
//        return paymentId;
//    }
//
//    public void setPaymentId(Long paymentId) {
//        this.paymentId = paymentId;
//    }
//
//    public Double getAmount() {
//        return amount;
//    }
//
//    public void setAmount(Double amount) {
//        this.amount = amount;
//    }
//
//    public PaymentStatus getStatus() {
//        return status;
//    }
//
//    public void setStatus(PaymentStatus status) {
//        this.status = status;
//    }
//
//    public String getPaymentMethod() {
//        return paymentMethod;
//    }
//
//    public void setPaymentMethod(String paymentMethod) {
//        this.paymentMethod = paymentMethod;
//    }
//
//    public String getRazorpayOrderId() {
//        return razorpayOrderId;
//    }
//
//    public void setRazorpayOrderId(String razorpayOrderId) {
//        this.razorpayOrderId = razorpayOrderId;
//    }
//
//    public String getRazorpayPaymentId() {
//        return razorpayPaymentId;
//    }
//
//    public void setRazorpayPaymentId(String razorpayPaymentId) {
//        this.razorpayPaymentId = razorpayPaymentId;
//    }
//
//    public String getRazorpaySignature() {
//        return razorpaySignature;
//    }
//
//    public void setRazorpaySignature(String razorpaySignature) {
//        this.razorpaySignature = razorpaySignature;
//    }
//
//    public Booking getBooking() {
//        return booking;
//    }
//
//    public void setBooking(Booking booking) {
//        this.booking = booking;
//    }
//
//    public LocalDateTime getCreatedAt() {
//        return createdAt;
//    }
//
//    public void setCreatedAt(LocalDateTime createdAt) {
//        this.createdAt = createdAt;
//    }
//
//    public Payment() {
//    }
//
//    public Payment(Long paymentId, Double amount, PaymentStatus status, String paymentMethod, String razorpayOrderId, String razorpayPaymentId, String razorpaySignature, Booking booking, LocalDateTime createdAt) {
//        this.paymentId = paymentId;
//        this.amount = amount;
//        this.status = status;
//        this.paymentMethod = paymentMethod;
//        this.razorpayOrderId = razorpayOrderId;
//        this.razorpayPaymentId = razorpayPaymentId;
//        this.razorpaySignature = razorpaySignature;
//        this.booking = booking;
//        this.createdAt = createdAt;
//    }
}
