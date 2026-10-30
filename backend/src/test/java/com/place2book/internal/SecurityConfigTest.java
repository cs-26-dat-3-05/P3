package com.place2book.internal;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

import com.place2book.internal.config.SecurityConfig;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class SecurityConfigTest {


    static ResultMatcher notBlockedBySecurity() {
        return result -> {
            int status = result.getResponse().getStatus();
            if (status == 401 || status == 403 || (status == 302 && result.getResponse().getRedirectedUrl().contains("/login"))) {
                throw new AssertionError("Request was blocked by security, status: " + status);
            }
        };
    }

    @Nested
    @WebMvcTest 
    @Import(SecurityConfig.class)
    @ActiveProfiles("dev")
    class DevProfile {
        @Autowired MockMvc mvc;

        @Test void healthIsOpen() throws Exception {
            mvc.perform(get("/actuator/health"))
                .andExpect(notBlockedBySecurity());
        }

        @Test void apiDocsAreOpen() throws Exception {
            mvc.perform(get("/v3/api-docs"))
                .andExpect(notBlockedBySecurity());
        }
    } 


    @Nested 
    @WebMvcTest  
    @Import(SecurityConfig.class)
    @ActiveProfiles("prod")
    class DefaultProfile {
        @Autowired MockMvc mvc;

        @Test void healthIsOpen() throws Exception {
            mvc.perform(get("/actuator/health"))
                .andExpect(notBlockedBySecurity());
        }

        @Test void apiDocsAreOpen() throws Exception {
            mvc.perform(get("/v3/api-docs"))
                .andExpect(notBlockedBySecurity());
        }
        
        /* 
        We dont have any endpoints yet, so i can't test this
        TODO: add test for endpoints 
            @Test void apiRequiresAuthentication() throws Exception {
            mvc.perform(get("/api/random-endpoint"))
                .andExpect(status().is3xxRedirection());
        }
        */
    }
}
