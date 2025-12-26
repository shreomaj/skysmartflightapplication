package com.ai.skysmart.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class SmsService {
    @Value("${sms.msg91.authkey}")
    private String authKey;

    @Value("${sms.msg91.sender}")
    private String sender;

    @Value("${sms.msg91.template-id}")
    private String templateId;

    private final RestTemplate restTemplate = new RestTemplate();

    public void sendOtp(String phone, String otp) {
        System.out.println("Sending OTP " + otp + " to " + phone);
        String url = "https://api.msg91.com/api/v5/otp";

        HttpHeaders headers = new HttpHeaders();
        headers.set("authkey", authKey);
        headers.setContentType(MediaType.APPLICATION_JSON);

        String body = """
        {
          "template_id": "%s",
          "mobile": "91%s",
          "authkey": "%s",
          "sender": "%s",
          "otp": "%s"
        }
        """.formatted(templateId, phone, authKey, sender, otp);

        HttpEntity<String> entity = new HttpEntity<>(body, headers);

        restTemplate.postForEntity(url, entity, String.class);
    }
        // Integrate Twilio / MSG91 / Fast2SMS here

}
