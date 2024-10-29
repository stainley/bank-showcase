package com.salapp.bank.userservice.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CustomAccessDeniedHandlerTest {

    private CustomAccessDeniedHandler accessDeniedHandler;
    private HttpServletRequest mockRequest;
    private HttpServletResponse mockResponse;
    private AccessDeniedException accessDeniedException;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule()); // Register the JavaTimeModule

        accessDeniedHandler = new CustomAccessDeniedHandler(objectMapper);
        mockRequest = mock(HttpServletRequest.class);
        mockResponse = mock(HttpServletResponse.class);
        accessDeniedException = new AccessDeniedException("Forbidden");
    }

    @Test
    void testHandleAccessDenied() throws Exception {
        // Arrange
        StringWriter responseWriter = new StringWriter();
        when(mockResponse.getWriter()).thenReturn(new PrintWriter(responseWriter));

        // Act
        accessDeniedHandler.handle(mockRequest, mockResponse, accessDeniedException);

        // Assert response status and content type
        verify(mockResponse).setStatus(HttpServletResponse.SC_FORBIDDEN);
        verify(mockResponse).setContentType("application/json");

        // Create expected error response
        ErrorResponse expectedErrorResponse = ErrorResponse.builder()
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

        // Serialize expected error response for comparison
        String expectedJsonResponse = objectMapper.writeValueAsString(expectedErrorResponse);
        assertNotNull(expectedJsonResponse);

        // Deserialize actual response content to compare with expected
        ErrorResponse actualResponse = objectMapper.readValue(responseWriter.toString(), ErrorResponse.class);

        // Verify the fields in the actual response
        assertEquals(expectedErrorResponse.getStatus(), actualResponse.getStatus());
        assertEquals(expectedErrorResponse.getError(), actualResponse.getError());
        assertEquals(expectedErrorResponse.getMessage(), actualResponse.getMessage());
        assertEquals(expectedErrorResponse.getDetails().getReason(), actualResponse.getDetails().getReason());
        assertEquals(expectedErrorResponse.getDetails().getDescription(), actualResponse.getDetails().getDescription());
        assertEquals(expectedErrorResponse.getSuggestions(), actualResponse.getSuggestions());
    }
}