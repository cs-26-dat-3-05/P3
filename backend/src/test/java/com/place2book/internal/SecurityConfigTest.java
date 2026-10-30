package com.place2book.internal;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class SecurityConfigTest {
    

    @Nested
    @SpringBootTest 
    @AutoConfigureMockMvc
    @ActiveProfiles("dev")
    class DevProfile {
        @Autowired MockMvc mvc;

        @Test void healthIsOpen() throws Exception {
            mvc.perform(get("/actuator/health"))
                .andExpect(status().isOk());
        }

        @Test void apiDocsAreOpen() throws Exception {
            mvc.perform(get("/v3/api-docs"))
                .andExpect(status().isOk());
        }
    } 


    @Nested 
    @SpringBootTest 
    @AutoConfigureMockMvc 
    class DefaultProfile {
        @Autowired MockMvc mvc;

        @Test void healthIsOpen() throws Exception {
            mvc.perform(get("/actuator/health"))
                .andExpect(status().isOk());
        }

        @Test void apiDocsAreOpen() throws Exception {
            mvc.perform(get("/v3/api-docs"))
                .andExpect(status().isOk());
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
