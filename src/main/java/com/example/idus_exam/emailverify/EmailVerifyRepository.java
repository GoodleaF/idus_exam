package com.example.idus_exam.emailverify;

import com.example.idus_exam.emailverify.model.EmailVerify;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmailVerifyRepository extends JpaRepository<EmailVerify, Long> {
    Optional<EmailVerify> findByUuid(String uuid);
}
