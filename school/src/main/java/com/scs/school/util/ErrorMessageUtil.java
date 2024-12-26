package com.scs.school.util;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Locale;
import java.util.ResourceBundle;

@Component
public class ErrorMessageUtil {
    @Value("${application.language}")
    private String currentLanguage;

    private static final String BASE_PATH = "language";
    private ResourceBundle resourceBundle;

    @PostConstruct
    public void init() {
        loadResourceBundle();
    }

    private void loadResourceBundle() {
        Locale locale = new Locale(currentLanguage);
        resourceBundle = ResourceBundle.getBundle(BASE_PATH + "." + currentLanguage + ".error_messages", locale);

        String x = currentLanguage;
    }

    public String getMessage(String key) {
        if (resourceBundle == null) {
            loadResourceBundle();
        }

        return resourceBundle.getString(key);
    }

    public String getCurrentLanguage() {
        return currentLanguage;
    }

    public void setCurrentLanguage(String currentLanguage) {
        this.currentLanguage = currentLanguage;
        loadResourceBundle();
    }
}
