package com.microservices.OrderService.external.client.decoder;

import com.microservices.OrderService.exception.OrderServiceCustomException;
import com.microservices.OrderService.external.response.ErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;

@Log4j2
public class CustomErrorDecoder implements ErrorDecoder {
    @Override
    public Exception decode(String s, Response response) {
        ObjectMapper objectMapper = new ObjectMapper();

        log.info("::{}", response.request().url());
        log.info("::{}", response.request().headers());

        try {
            ErrorResponse errorResponse = objectMapper.readValue(response.body().asInputStream(), ErrorResponse.class);
            return new OrderServiceCustomException(errorResponse.getErrorMessage(),
                    errorResponse.getErrorCode(), response.status());
        } catch (IOException e) {
            throw new OrderServiceCustomException("Internal setrver error", "INTERNAL_SERVER_ERROR", 500);
        }

    }
}
