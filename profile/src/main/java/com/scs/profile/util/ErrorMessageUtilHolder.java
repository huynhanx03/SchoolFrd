package com.scs.profile.util;

import com.scs.profile.util.ErrorMessageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ErrorMessageUtilHolder {
    private static ErrorMessageUtil errorMessageUtil;

    @Autowired
    public void setErrorMessageUtil(ErrorMessageUtil errorMessageUtil) {
        ErrorMessageUtilHolder.errorMessageUtil = errorMessageUtil;
    }

    public static ErrorMessageUtil getErrorMessageUtil() {
        return errorMessageUtil;
    }
}