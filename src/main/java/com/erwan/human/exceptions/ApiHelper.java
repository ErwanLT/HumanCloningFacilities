package com.erwan.human.exceptions;

import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Response;
import feign.codec.ErrorDecoder;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;

import java.nio.charset.StandardCharsets;

public class ApiHelper {

    private ApiHelper() {
        //utility class, forbidden constructor
    }

    public static <E extends ApiException> ErrorDecoder errorDecoder(Class<E> clazzE) {
        return (methodKey, response) -> {
            ApiError apiError = readError(response);
            String msg = StringUtils.hasLength(apiError.getMessage()) ?
                    apiError.getMessage() :
                    HttpStatus.resolve(response.status()).getReasonPhrase();
            int code = apiError.getStatusCode();

            return apiException(msg, code, clazzE);
        };
    }

    private static ApiError readError(Response response) {
        ObjectMapper mapper = new ObjectMapper();
        ApiError error;
        try {
            error = mapper.readValue(response.body().asReader(StandardCharsets.UTF_8), ApiError.class);
        } catch (Exception e) {
            error = new ApiError(response.status(), response.reason());
        }
        return error;
    }

    private static <E extends ApiException> E apiException(String msg, int code, Class<E> clazz) {
        try {
            return clazz.getConstructor(String.class, int.class).newInstance(msg, code);
        } catch (Exception e) {
            throw new TechnicalException(e.getMessage(), e);
        }
    }

}
