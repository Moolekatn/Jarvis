package com.fluxkart.Jarvis.test;

import com.fluxkart.Jarvis.entity.Contact;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(com.fluxkart.Jarvis.controller.ContactController.class)
public class ContactController {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ContactService contactService;

    @Test
    public void testIdentifyContact_Success() throws Exception {
        // Sample JSON payload for request
        String requestBody = "{\"email\": \"test@example.com\", \"phoneNumber\": \"1234567890\"}";

        // Mock response from service layer
        Contact identifiedContact = new Contact();
        identifiedContact.setEmail("test@example.com");
        identifiedContact.setPhoneNumber("1234567890");
        when(contactService.identifyContact(any(String.class), any(String.class)))
                .thenReturn(identifiedContact);

        // Perform POST request to /identify endpoint
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/identify")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        // Verify response body contains the expected contact details
        String responseBody = result.getResponse().getContentAsString();
        assertTrue(responseBody.contains("test@example.com"));
        assertTrue(responseBody.contains("1234567890"));
    }

    @Test
    public void testIdentifyContact_NoEmail() throws Exception {
        // Sample JSON payload for request with no email
        String requestBody = "{\"phoneNumber\": \"1234567890\"}";

        // Perform POST request to /identify endpoint
        mockMvc.perform(MockMvcRequestBuilders.post("/identify")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Email or phoneNumber must be provided"));
    }

    @Test
    public void testIdentifyContact_NoPhoneNumber() throws Exception {
        // Sample JSON payload for request with no phone number
        String requestBody = "{\"email\": \"test@example.com\"}";

        // Perform POST request to /identify endpoint
        mockMvc.perform(MockMvcRequestBuilders.post("/identify")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Email or phoneNumber must be provided"));
    }

    // Add more test methods to cover other scenarios
}
