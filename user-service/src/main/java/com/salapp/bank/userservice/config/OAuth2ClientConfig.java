package com.salapp.bank.userservice.config;

import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;

@SuppressWarnings("unused")
public class OAuth2ClientConfig {


    public ClientRegistrationRepository clientRegistrationRepository() {
        ClientRegistration googleRegistration = googleClientRegistration();
        return new InMemoryClientRegistrationRepository(googleRegistration);
    }

    private ClientRegistration googleClientRegistration() {
        return ClientRegistration.withRegistrationId("google")
                .clientId("your-google-client-id")
                .clientSecret("")
                .scope("openid", "profile", "email")
                .authorizationUri("https://accounts.google.com/o/oauth2/auth")
                .tokenUri("https://oauth2.googleapis.com/token")
                .userInfoUri("https://www.googleapis.com/oauth2/v3/userinfo")
                .userNameAttributeName("sub")
                .redirectUri("{baseUrl}/login/oauth2/code/google")
                .clientName("Google")
                .build();
    }
}
