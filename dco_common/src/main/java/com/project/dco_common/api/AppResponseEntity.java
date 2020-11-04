package com.project.dco_common.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class AppResponseEntity<T> extends ResponseEntity<ResponseBody<T>> {

    private static final HttpStatusResponse status = new HttpStatusResponse("OK", HttpStatus.OK.value(), "");

    public AppResponseEntity(ResponseBody<T> body, HttpStatus status) {
        super(body, status);
    }


    public static <T> AppResponseEntity<T> withError(HttpStatusResponse status) {
        return new AppResponseEntity<T>(
                ResponseBody.<T>builder().status(status).build(),
                HttpStatus.valueOf(status.getHttp()));
    }

    public static <T> AppResponseEntity<T> withError(T data, HttpStatusResponse status) {
        return new AppResponseEntity<T>(
                ResponseBody.<T>builder().data(data).status(status).build(), HttpStatus.valueOf(status.getHttp()));
    }

    public static <T> AppResponseEntity<T> withSuccess(T data) {
        status.setMessage("Successfully!");
        return new AppResponseEntity<T>(
                ResponseBody.<T>builder().data(data).status(status).build(), HttpStatus.OK);
    }

    public static <T> AppResponseEntity<T> withSuccess(T data, String message) {
        status.setMessage(message);
        return new AppResponseEntity<T>(
                ResponseBody.<T>builder().data(data).status(status).build(), HttpStatus.OK);
    }

}
