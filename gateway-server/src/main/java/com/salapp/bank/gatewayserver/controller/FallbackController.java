package com.salapp.bank.gatewayserver.controller;

import com.salapp.bank.gatewayserver.util.FallbackMessageProvider;
import com.salapp.bank.gatewayserver.util.FallbackResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * Fallback controller for handling service unavailability.
 * <p>
 * Provides a standardized error response when a service is unavailable.
 */
@RestController
public class FallbackController {

    /**
     * Fallback message provider for retrieving error messages.
     */
    private final FallbackMessageProvider fallbackMessageProvider;

    /**
     * Constructs a FallbackController instance.
     *
     * @param fallbackMessageProvider Fallback message provider
     */
    public FallbackController(final FallbackMessageProvider fallbackMessageProvider) {
        this.fallbackMessageProvider = fallbackMessageProvider;
    }


    /**
     * Handles service fallback requests.
     * <p>
     * Returns a standardized error response when a service is unavailable.
     *
     * @param service Service name
     * @return ResponseEntity with error response
     */
    @GetMapping(value = "/fallback/{service}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<FallbackResponse> serviceFallback(@PathVariable String service) {
        String fallbackMessage = fallbackMessageProvider.getFallbackMessage(service);
        FallbackResponse fallbackResponse = new FallbackResponse(HttpStatus.SERVICE_UNAVAILABLE.name(), fallbackMessage);
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                .header("Retry-After", "300")
                .body(fallbackResponse);
    }

}
