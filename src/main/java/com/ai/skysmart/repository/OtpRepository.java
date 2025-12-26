package com.ai.skysmart.repository;

import com.ai.skysmart.entity.OtpVerification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OtpRepository extends JpaRepository<OtpVerification, String> {
}
