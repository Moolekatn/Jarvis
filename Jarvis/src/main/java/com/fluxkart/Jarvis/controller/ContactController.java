package com.fluxkart.Jarvis.controller;
import com.fluxkart.Jarvis.entity.Contact;
import com.fluxkart.Jarvis.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ContactController {

    @Autowired
    private ContactService contactService;

    @PostMapping("/identify")
    public ResponseEntity<?> identifyContact(@RequestBody ContactRequest request) {
        String email = request.getEmail();
        String phoneNumber = request.getPhoneNumber();

        // Validate that either email or phoneNumber is provided
        if (email == null && phoneNumber == null) {
            return ResponseEntity.badRequest().body("Either email or phoneNumber must be provided");
        }

        // Call the service layer to identify the contact
        Contact identifiedContact = contactService.identifyContact(email, phoneNumber);

        // Return the identified contact in the response
        return ResponseEntity.ok(identifiedContact);
    }
}
