package com.scs.notification.exception;

import com.scs.notification.util.ErrorMessageUtilHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

public enum ErrorCode {
    // Generic error codes
    UNCATEGORIZED_EXCEPTION(504, HttpStatus.INTERNAL_SERVER_ERROR, getErrorMessage("UNCATEGORIZED_EXCEPTION")),
    INVALID_KEY(999, HttpStatus.BAD_REQUEST, getErrorMessage("KEY_INVAILD")),
    EXISTED(1001, HttpStatus.BAD_REQUEST, ""),
    INVALID(1002, HttpStatus.BAD_REQUEST, ""),
    NOT_EXISTED(1003, HttpStatus.NOT_FOUND, ""),

    // HTTP-related error codes
    HTTP_BAD_REQUEST(400, HttpStatus.BAD_REQUEST, getErrorMessage("HTTP_BAD_REQUEST")),
    HTTP_UNAUTHORIZED(401, HttpStatus.UNAUTHORIZED, getErrorMessage("HTTP_UNAUTHORIZED")),
    HTTP_NOT_FOUND(404, HttpStatus.NOT_FOUND, getErrorMessage("HTTP_NOT_FOUND")),
    HTTP_INTERNAL_SERVER_ERROR(500, HttpStatus.INTERNAL_SERVER_ERROR, getErrorMessage("HTTP_INTERNAL_SERVER_ERROR"));

    private int code;
    private HttpStatusCode httpStatusCode;
    private String message;

    ErrorCode(int code, HttpStatusCode httpStatusCode, String message) {
        this.code = code;
        this.httpStatusCode = httpStatusCode;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public HttpStatusCode getHttpStatusCode() {
        return httpStatusCode;
    }

    public void setHttpStatusCode(HttpStatusCode httpStatusCode) {
        this.httpStatusCode = httpStatusCode;
    }

    private static String getErrorMessage(String key) {
        return ErrorMessageUtilHolder.getErrorMessageUtil().getMessage(key);
    }
}
