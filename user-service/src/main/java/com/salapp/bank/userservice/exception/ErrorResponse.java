package com.salapp.bank.userservice.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class ErrorResponse {

    private int status; // HTTP status code
    private String error; // Error name (for example, Conflict)
    private String message; // General message about what went wrong
    private Details details; // Nested object for detailed error description
    private String suggestions; // Suggestions for the user

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private LocalDateTime timestamp; // Timestamp of the error

    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Getter
    public static class Details {
        private String reason; // Reason for the error (for example, Duplicate Entry)
        private String field;  // The field that caused the error (for example, email)
        private String description; // Detailed description of the issue
    }
}
