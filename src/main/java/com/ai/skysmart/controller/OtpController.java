package com.ai.skysmart.controller;

import com.ai.skysmart.service.OtpService;
import com.ai.skysmart.service.SmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OtpController {

    @Autowired
    private OtpService otpService;
    @Autowired
    private SmsService smsService;

    @PostMapping("/auth/send-otp")
    public ResponseEntity<String> sendOtp(@RequestParam String phone) {
        String otp = String.valueOf((int)(Math.random() * 900000) + 100000);
        otpService.saveOtp(phone, otp);

        // In real application, send OTP via SMS gateway here

        smsService.sendOtp(phone, otp);
        return ResponseEntity.ok("OTP sent to " + phone +"successfully");
    }
}
