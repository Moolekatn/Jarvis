package com.fluxkart.Jarvis.repository;
import com.fluxkart.Jarvis.entity.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Long> {
    Optional<Contact> findByEmailOrPhoneNumber(String email, String phoneNumber);
    // You can add custom query methods here if needed
}




