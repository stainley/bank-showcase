package com.salapp.bank.gatewayserver.controller;

import com.salapp.bank.gatewayserver.util.FallbackMessageProvider;
import com.salapp.bank.gatewayserver.util.FallbackResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Fallback controller for handling service unavailability.
 * <p>
 * Provides a standardized error response when a service is unavailable.
 */
@RestController
@RequestMapping("/fallback")
public class FallbackController {

    /**
     * Fallback message provider for retrieving error messages.
     */
    private final FallbackMessageProvider fallbackMessageProvider;


    public FallbackController(final FallbackMessageProvider fallbackMessageProvider) {
        this.fallbackMessageProvider = fallbackMessageProvider;
    }


    @PostMapping(value = "/{service}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<FallbackResponse> handlePostFallback(@PathVariable String service) {
        return buildFallbackResponse(service);
    }


    @GetMapping(value = "/{service}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<FallbackResponse> handleGetFallback(@PathVariable String service) {
        return buildFallbackResponse(service);
    }

    private ResponseEntity<FallbackResponse> buildFallbackResponse(String service) {
        String fallbackMessage = fallbackMessageProvider.getFallbackMessage(service);
        FallbackResponse fallbackResponse = new FallbackResponse(HttpStatus.SERVICE_UNAVAILABLE.name(), fallbackMessage);
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                .header("Retry-After", "300")
                .body(fallbackResponse);
    }
}
