package com.salapp.bank.gatewayserver.exception;

import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.reactive.error.DefaultErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;

import java.util.Map;

@Component
public class CustomErrorAttributes extends DefaultErrorAttributes {

    @Override
    public Map<String, Object> getErrorAttributes(ServerRequest request, ErrorAttributeOptions options) {
        Map<String, Object> errorAttributes = super.getErrorAttributes(request, options);
        Throwable error = getError(request);

        if (error instanceof UnauthorizedException) {
            errorAttributes.put("status", HttpStatus.UNAUTHORIZED.value());
            errorAttributes.put("error", "Unauthorized");
            errorAttributes.put("message", error.getMessage());
        }

        return errorAttributes;
    }
}
