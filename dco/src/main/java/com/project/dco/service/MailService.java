package com.project.dco.service;

import com.project.dco.dto.model.Mail;
import com.project.dco.dto.request.SendMailRequest;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface MailService {

    Optional<Mail> sendMail(SendMailRequest sendMailRequest, MultipartFile template[], MultipartFile[] files);

    Optional<Mail> sendMail(SendMailRequest sendMailRequest);

    Optional<Mail> mailTo(SendMailRequest sendMailRequest, MultipartFile template[], MultipartFile[] files) throws MessagingException, IOException;

    List<?> getMailList(int page, int perPage, String searchField, String searchText, String sortField,
                        String sortType, String dateField, String timeForm, String timeTo);
}
