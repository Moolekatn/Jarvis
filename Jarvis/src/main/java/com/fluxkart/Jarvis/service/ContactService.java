package com.fluxkart.Jarvis.service;

import com.fluxkart.Jarvis.entity.Contact;
import com.fluxkart.Jarvis.repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ContactService {

    @Autowired
    private ContactRepository contactRepository;

    public Contact identifyContact(String email, String phoneNumber) {
        // First, check if any existing contact matches the provided email or phone number
        Optional<Contact> existingContact = contactRepository.findByEmailOrPhoneNumber(email, phoneNumber);

        // If an existing contact is found, return it
        if (existingContact.isPresent()) {
            return existingContact.get();
        } else {
            // If no existing contact matches, create a new one
            Contact newContact = new Contact();
            newContact.setEmail(email);
            newContact.setPhoneNumber(phoneNumber);
            newContact.setLinkPrecedence("primary");

            // Save the new contact to the database
            return contactRepository.save(newContact);
        }
    }

    public Contact createOrUpdateContact(String email, String phoneNumber) {
        // First, check if any existing contact matches the provided email or phone number
        Optional<Contact> existingContact = contactRepository.findByEmailOrPhoneNumber(email, phoneNumber);

        // If an existing contact is found, update it
        if (existingContact.isPresent()) {
            Contact contactToUpdate = existingContact.get();
            // Update the email and phone number if they are different
            if (!email.equals(contactToUpdate.getEmail())) {
                contactToUpdate.setEmail(email);
            }
            if (!phoneNumber.equals(contactToUpdate.getPhoneNumber())) {
                contactToUpdate.setPhoneNumber(phoneNumber);
            }
            // Update the contact in the database
            return contactRepository.save(contactToUpdate);
        } else {
            // If no existing contact matches, create a new one
            Contact newContact = new Contact();
            newContact.setEmail(email);
            newContact.setPhoneNumber(phoneNumber);
            newContact.setLinkPrecedence("primary");

            // Save the new contact to the database
            return contactRepository.save(newContact);
        }
    }
}