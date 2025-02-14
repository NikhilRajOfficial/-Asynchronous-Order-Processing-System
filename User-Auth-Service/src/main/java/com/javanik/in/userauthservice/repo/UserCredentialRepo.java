package com.javanik.in.userauthservice.repo;

import com.javanik.in.userauthservice.entity.UserCredential;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserCredentialRepo extends JpaRepository<UserCredential , Integer> {
    Optional<UserCredential> findByName(String username);
}
