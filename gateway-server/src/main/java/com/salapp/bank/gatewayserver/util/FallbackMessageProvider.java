package com.salapp.bank.gatewayserver.util;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * Provides fallback messages for service unavailability.
 */
@Component
public class FallbackMessageProvider {

    /**
     * Mapping of service names to fallback messages.
     */
    private static final Map<String, String> FALLBACK_MESSAGES = new HashMap<>();

    static {
        FALLBACK_MESSAGES.put("account", "Account service is currently unavailable. Please try again later.");
        FALLBACK_MESSAGES.put("transaction", "Transaction service is currently unavailable. Please try again later.");
        FALLBACK_MESSAGES.put("authentication", "Authentication service is currently unavailable. Please try again later.");
    }

    /**
     * Retrieves the fallback message for a given service.
     * <p/>
     *
     * @param service Service name
     * @return Fallback message
     */
    public String getFallbackMessage(String service) {
        return FALLBACK_MESSAGES.getOrDefault(service, "Service is currently unavailable. Please try again later.");
    }
}
