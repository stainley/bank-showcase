package com.salapp.bank.userservice.security;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.web.util.matcher.RequestMatcher;


import static org.junit.jupiter.api.Assertions.*;

class CustomCORSConfigurationTest {
    private RequestMatcher customRequestMatcher;
    private MockHttpServletRequest request;


    @BeforeEach
    void setUp() {
        customRequestMatcher = new CustomRequestMatcher();
        request = new MockHttpServletRequest();
    }

    @ParameterizedTest
    @CsvSource({
            "POST, /api/test, true",
            "PUT, /api/test, true",
            "DELETE, /api/test, true",
            "PATCH, /api/test, true",
            "GET, /api/test, false",
            "POST, /other/test, false",
            "GET, /other/test, false"
    })
    void testCustomRequestMatcher(String method, String uri, boolean expectedResult) {
        request.setMethod(method);
        request.setRequestURI(uri);

        assertEquals(expectedResult, customRequestMatcher.matches(request),
                String.format("%s request to %s should %s", method, uri, expectedResult ? "match" : "not match"));
    }
}