package com.ai.skysmart.service.impl;

import com.ai.skysmart.entity.OtpVerification;
import com.ai.skysmart.repository.OtpRepository;
import com.ai.skysmart.service.OtpService;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

public class OtpServiceImpl implements OtpService {
    @Autowired
    private OtpRepository otpRepo;

    @Override
    public void saveOtp(String phoneNumber, String otpNumber) {
        OtpVerification otpEntity=new OtpVerification();
        otpEntity.setPhoneNumber(phoneNumber);
        otpEntity.setOtp(otpNumber);
        otpEntity.setExpiryTime(LocalDateTime.now().plusMinutes(5));
        otpRepo.save(otpEntity);

    }

    @Override
    public boolean verifyOtp(String phoneNumber, String otp) {
        OtpVerification otpEntity=otpRepo.findById(phoneNumber).orElse(null);
        if(otpEntity!=null){
            if(otpEntity.getOtp().equals(otp) &&
                    otpEntity.getExpiryTime().isAfter(LocalDateTime.now())){
                otpRepo.delete(otpEntity);
                return true;
            }
        }
        return false;
    }
}
