package com.place2book.internal.common;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.place2book.internal.common.exceptions.NotFoundException;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler handler = new GlobalExceptionHandler();

    @Test
    @DisplayName("NotFoundException returns 404 with the exception message")
    void notFoundReturns404() {
        ProblemDetail problem = handler.handleNotFound(new NotFoundException("Payout with id 505 not found"));

        assertThat(problem.getStatus()).isEqualTo(404);
        assertThat(problem.getDetail()).isEqualTo("Payout with id 505 not found");
    }

    @Test
    @DisplayName("Return 500 without leaking internal details")
    void unexpectedReturns500WithoutDetails() {
        ProblemDetail problem = handler.handleUnexpected(new RuntimeException("secret SQL details"));

        assertThat(problem.getStatus()).isEqualTo(500);
        assertThat(problem.getDetail()).doesNotContain("secret SQL details");
    }

    @Test
    @DisplayName("Validation errors return 400 with one error per field")
    void validationReturns400WithFieldErrors() {
        BeanPropertyBindingResult bindingResult = new BeanPropertyBindingResult(new Object(), "request");
        bindingResult.addError(new FieldError("request", "amount", "must be greater than 0"));

        MethodArgumentNotValidException ex = mock(MethodArgumentNotValidException.class);
        when(ex.getBindingResult()).thenReturn(bindingResult);

        ResponseEntity<Object> response = handler.handleMethodArgumentNotValid(ex, new HttpHeaders(),
                HttpStatus.BAD_REQUEST, null);

        assertThat(response.getStatusCode().value()).isEqualTo(400);
        ProblemDetail problem = (ProblemDetail) response.getBody();
        assertThat(problem.getProperties()).containsEntry("errors", Map.of("amount", "must be greater than 0"));
    }
}