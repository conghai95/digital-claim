package com.project.dco.service;

import com.project.dco.dto.model.Mail;
import com.project.dco.dto.request.SendMailRequest;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.List;

public interface MailService {

    void sendMail(SendMailRequest sendMailRequest, MultipartFile[] files) throws MessagingException, IOException;

    void sendMail(Mail mail, MultipartFile[] files) throws MessagingException, IOException;

    List<?> getHistoryByEmail(String email);
}
