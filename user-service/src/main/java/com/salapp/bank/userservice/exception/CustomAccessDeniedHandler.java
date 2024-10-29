package com.salapp.bank.userservice.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;

/**
 * Custom implementation of AccessDeniedHandler to handle access denied exceptions.
 * This handler returns a JSON error response with relevant details when access is denied.
 */
@RequiredArgsConstructor
@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    /**
     * ObjectMapper instance used for serializing error responses to JSON.
     */
    private final ObjectMapper objectMapper;

    /**
     * Handles access denied exceptions by returning a JSON error response.
     *
     * @param request               HttpServletRequest object
     * @param response              HttpServletResponse object
     * @param accessDeniedException AccessDeniedException instance
     * @throws IOException if writing to response fails
     */
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException {

        // Set response status and content type
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType("application/json");

        ErrorResponse errorResponse = ErrorResponse.builder()
                .status(HttpStatus.FORBIDDEN.value())
                .error(HttpStatus.FORBIDDEN.getReasonPhrase())
                .message("Access denied due to insufficient privileges")
                .details(ErrorResponse.Details.builder()
                        .reason("Insufficient Privileges")
                        .description("You do not have the required permissions to perform this action.")
                        .build())
                .suggestions("Please contact your administrator if you believe this is an error.")
                .timestamp(LocalDateTime.now())
                .build();

        // Write error response to output stream
        response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
    }
}
