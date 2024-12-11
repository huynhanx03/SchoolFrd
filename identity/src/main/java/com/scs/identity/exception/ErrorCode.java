package com.scs.identity.exception;

import com.scs.identity.util.ErrorMessageUtilHolder;

public enum ErrorCode {
    // Generic error codes
    UNCATEGORIZED_EXCEPTION(504, getErrorMessage("UNCATEGORIZED_EXCEPTION")),
    INVALID_KEY(1000, getErrorMessage("KEY_INVAILD")),
    EXISTED(1001, ""),
    INVALID(1002, ""),
    NOT_EXISTED(1003, ""),

    // HTTP-related error codes
    HTTP_BAD_REQUEST(400, getErrorMessage("HTTP_BAD_REQUEST")),
    HTTP_UNAUTHORIZED(401, getErrorMessage("HTTP_UNAUTHORIZED")),
    HTTP_NOT_FOUND(404, getErrorMessage("HTTP_NOT_FOUND")),
    HTTP_INTERNAL_SERVER_ERROR(500, getErrorMessage("HTTP_INTERNAL_SERVER_ERROR"));

    private int code;
    private String message;

    ErrorCode(int code, String message) {
        this.code = code;
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

    private static String getErrorMessage(String key) {
        return ErrorMessageUtilHolder.getErrorMessageUtil().getMessage(key);
    }
}

