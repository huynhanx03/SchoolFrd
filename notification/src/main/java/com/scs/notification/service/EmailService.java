package com.scs.notification.service;

import com.scs.notification.dto.request.EmailRequest;
import com.scs.notification.dto.request.SendEmailRequest;
import com.scs.notification.dto.request.Sender;
import com.scs.notification.dto.response.EmailResponse;
import com.scs.notification.exception.AppException;
import com.scs.notification.exception.ErrorCode;
import com.scs.notification.repository.httpclient.EmailClient;
import feign.FeignException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class EmailService {
    EmailClient emailClient;

    @Value("${notification.email.brevo-apikey}")
    @NonFinal
    String apiKey;

    @Value("${notification.email.admin}")
    @NonFinal
    String emailAdmin;

    @Value("${notification.email.sender-name}")
    @NonFinal
    String senderName;

    public EmailResponse sendEmail(SendEmailRequest request) {
        EmailRequest emailRequest = EmailRequest.builder()
                .sender(Sender.builder()
                        .name(senderName)
                        .email(emailAdmin)
                        .build())
                .to(List.of(request.getTo()))
                .subject(request.getSubject())
                .htmlContent(request.getHtmlContent())
                .build();
        try {
            return emailClient.sendEmail(apiKey, emailRequest);
        } catch (FeignException e){
            throw new AppException(ErrorCode.HTTP_BAD_REQUEST);
        }
    }
}
