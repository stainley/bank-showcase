package com.salapp.bank.userservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

/**
 * Global exception handler for user service.
 * <p>
 * Handles specific exceptions and returns standardized error responses.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles insufficient privilege exceptions.
     *
     * @param exception InsufficientPrivilegeException instance
     * @return ResponseEntity with error response
     */
    @ExceptionHandler(InsufficientPrivilegeException.class)
    public ResponseEntity<ErrorResponse> handleInsufficientPrivilege(InsufficientPrivilegeException exception) {
        return createErrorResponse(exception, HttpStatus.UNAUTHORIZED, "Insufficient Privilege");
    }

    /**
     * Handles user not found exceptions.
     *
     * @param exception UserNotFoundException instance
     * @return ResponseEntity with error response
     */
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFoundException(UserNotFoundException exception) {
        ErrorResponse.Details details = ErrorResponse.Details.builder()
                .reason("Resource Not Found")
                .field("userId")
                .description("The requested user could not be found in the system.")
                .build();

        return createErrorResponse(exception, HttpStatus.NOT_FOUND, details, "Please verify the user ID or contact support for further assistance.");
    }

    /**
     * Handles duplicate user exceptions.
     *
     * @param exception DuplicateUserException instance
     * @return ResponseEntity with error response
     */
    @ExceptionHandler(DuplicateUserException.class)
    public ResponseEntity<ErrorResponse> handleDuplicateUserException(DuplicateUserException exception) {
        ErrorResponse.Details details = ErrorResponse.Details.builder()
                .reason("Duplicate Entry")
                .field("Email")
                .description(exception.getMessage())
                .build();

        return createErrorResponse(exception, HttpStatus.CONFLICT, details, "Please use a different email address or login if you already have an account.");
    }


    /**
     * Creates a standardized error response.
     *
     * @param exception Exception instance
     * @param status    HttpStatus
     * @param reason    Error reason
     * @return ResponseEntity with error response
     */
    private ResponseEntity<ErrorResponse> createErrorResponse(Exception exception, HttpStatus status, String reason) {
        return createErrorResponse(exception, status, null, reason);
    }

    /**
     * Creates a standardized error response.
     *
     * @param exception  Exception instance
     * @param status     HttpStatus
     * @param details    Error details
     * @param suggestion Error suggestion
     * @return ResponseEntity with error response
     */
    private ResponseEntity<ErrorResponse> createErrorResponse(Exception exception, HttpStatus status, ErrorResponse.Details details, String suggestion) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .status(status.value())
                .error(status.getReasonPhrase())
                .message(exception.getMessage())
                .details(details)
                .suggestions(suggestion)
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseEntity.status(status).body(errorResponse);
    }

}
