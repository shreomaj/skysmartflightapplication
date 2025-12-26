package com.ai.skysmart.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.time.LocalDateTime;

@Entity
public class OtpVerification {
    @Id
    private String phoneNumber;

    private String otp;

    private LocalDateTime expiryTime;

    public OtpVerification() {
    }
    public OtpVerification(String phoneNumber, String otp, LocalDateTime expiryTime) {
        this.phoneNumber = phoneNumber;
        this.otp = otp;
        this.expiryTime = expiryTime;
    }
    public String getPhoneNumber() {
        return phoneNumber;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    public String getOtp() {
        return otp;
    }
    public void setOtp(String otp) {
        this.otp = otp;
    }
    public LocalDateTime getExpiryTime() {
        return expiryTime;
    }
    public void setExpiryTime(LocalDateTime expiryTime) {
        this.expiryTime = expiryTime;

    }

}
