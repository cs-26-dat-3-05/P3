package com.place2book.internal.common;

// https://www.baeldung.com/exception-handling-for-rest-with-spring

import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.place2book.internal.common.exceptions.NotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * Handles {@link NotFoundException} and returns 404 Not Found.
     * <p>
     * The exception message is sent to the client as {@code detail}, so it should
     * describe what was not found, e.g. "Organizer with id 42 not found".
     *
     * @param ex the exception thrown when a requested resource does not exist
     * @return a {@link ProblemDetail} with status 404
     */
    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ProblemDetail handleNotFound(NotFoundException ex) {
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
        problem.setTitle("Resource not found");
        return problem;
    }

    /**
     * Handles validation errors from {@code @Valid} request bodies and returns 400
     * Bad Request.
     * <p>
     * Collects every field that failed validation into an {@code errors} property,
     * mapping field name to error message, so the frontend can show each error
     * next to the right input field.
     *
     * @param ex      the exception thrown by Spring when validation fails
     * @param headers not used
     * @param status  not used
     * @param request not used
     * @return a {@link ProblemDetail} with status 400 and an {@code errors} map
     */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request) {

        Map<String, String> errors = new HashMap<>();
        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            errors.put(fieldError.getField(), fieldError.getDefaultMessage());
        }

        ProblemDetail problem = ProblemDetail.forStatusAndDetail(
                HttpStatus.BAD_REQUEST, "Invalid fields in request");
        problem.setTitle("Validation failed");
        problem.setProperty("errors", errors);

        return ResponseEntity.badRequest().body(problem);
    }

    /**
     * Handles {@link Exception} and returns 500 Internal Server Error.
     * <p>
     * Acts as a catch-all for any exception that does not have a more specific
     * handler.
     * The client always receives a generic message, never the exception message or
     * stack trace.
     * The full error is logged on the server instead.
     *
     * @param ex the exception thrown when an unexpected error occurs
     * @return a {@link ProblemDetail} with status 500 and a generic message
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ProblemDetail handleUnexpected(Exception ex) {
        logger.error("Unexpected error", ex);

        ProblemDetail problem = ProblemDetail.forStatusAndDetail(
                HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred. Please try again later.");
        problem.setTitle("Internal Server Error");
        return problem;
    }

}