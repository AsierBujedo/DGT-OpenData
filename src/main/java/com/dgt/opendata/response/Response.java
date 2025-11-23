package com.dgt.opendata.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Response<T> {

    private final int errorCode;
    private final String message;
    private final T data;

    public Response(int errorCode, String message) {
        this(errorCode, message, null);
    }

    public Response(T data) {
        this(0, "", data);
    }

    public Response(int errorCode, String message, T data) {
        this.errorCode = errorCode;
        this.message = message;
        this.data = data;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public String getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }

    public ResponseEntity<Response<T>> toResponseEntity() {
        if (this.errorCode != 0) {
            return toResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return toResponseEntity(HttpStatus.OK);
    }

    public ResponseEntity<Response<T>> toResponseEntity(HttpStatusCode status) {
        if (status == null) {
            if (this.errorCode == 0) {
                status = HttpStatus.OK;
            } else {
                status = HttpStatus.INTERNAL_SERVER_ERROR;
            }
        }
        return ResponseEntity.status(status).body(this);
    }
}
