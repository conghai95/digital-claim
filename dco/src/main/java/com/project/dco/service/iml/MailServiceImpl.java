package com.project.dco.service.iml;

import com.project.dco.dao.MailRepository;
import com.project.dco.dto.model.Mail;
import com.project.dco.dto.request.SendMailRequest;
import com.project.dco.service.MailService;
import com.project.dco_common.constants.DateTimeConstants;
import com.project.dco_common.utils.DateTimeUtils;
import com.project.dco_common.utils.PageableUtil;
import com.project.dco_common.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.*;
import javax.mail.internet.*;
import java.io.IOException;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

@Service
public class MailServiceImpl implements MailService {

    @Autowired
    private JavaMailSenderImpl emailSender;

    @Autowired
    private MailRepository mailRepository;

    // sending mail with default mail
    @Override
    public Mail sendMail(SendMailRequest sendMailRequest, MultipartFile[] files) throws MessagingException {
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setTo(sendMailRequest.getTo());
        helper.setCc(sendMailRequest.getCc());
        helper.setBcc(sendMailRequest.getBcc());
        helper.setSubject(sendMailRequest.getSubject());
        helper.setText(sendMailRequest.getContent(), true);

        if (files != null && files.length > 0) {
            for (MultipartFile multipartFile : files) {
                helper.addAttachment(multipartFile.getOriginalFilename(), multipartFile);
            }
        }
        emailSender.send(message);
        return saveMail(sendMailRequest);
    }

    // sending mail with dynamic mail from api
    @Override
    public Mail mailTo(SendMailRequest sendMailRequest, MultipartFile[] files) throws MessagingException, IOException {
        Message message = createMessageInstance(sendMailRequest);

        BodyPart messageBodyPart = new MimeBodyPart();
        messageBodyPart.setContent(sendMailRequest.getContent(), "text/html; charset=utf-8");

        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(messageBodyPart);

        if (files != null && files.length > 0) {
            for (MultipartFile filePath : files) {
                MimeBodyPart attachPart = new MimeBodyPart();
                attachPart.setContent(filePath.getBytes(), filePath.getContentType());
                attachPart.setFileName(filePath.getOriginalFilename());
                attachPart.setDisposition(Part.ATTACHMENT);
                multipart.addBodyPart(attachPart);
            }
        }
        message.setContent(multipart);
        Transport.send(message);
        return saveMail(sendMailRequest);
    }

    @Override
    public List<?> getMailList(int page, int perPage, String searchField, String searchText, String sortField,
                               String sortType, String dateField, String timeForm, String timeTo) {
        List<Mail> accounts = mailRepository.findAll();
        if (!StringUtils.isEmpty(searchField) && !StringUtils.isEmpty(searchText) ||
                !StringUtils.isEmpty(dateField) && !StringUtils.isEmpty(timeForm) && !StringUtils.isEmpty(timeTo) ||
                !StringUtils.isEmpty(sortField) && !StringUtils.isEmpty(sortType)) {
            accounts = accounts.stream().filter(e -> PageableUtil.searchObjectByField(e, searchField, searchText)).collect(Collectors.toList());
        }
        if (!StringUtils.isEmpty(dateField) && !StringUtils.isEmpty(timeForm) && !StringUtils.isEmpty(timeTo)) {
            accounts = accounts.stream().filter(e -> PageableUtil.filterByDate(e, dateField, timeForm, timeTo)).collect(Collectors.toList());
        }
        if (!StringUtils.isEmpty(sortField) && !StringUtils.isEmpty(sortType)) {
            try {
                accounts = PageableUtil.getSort(accounts, sortField, sortType, Mail.class);
            } catch (NoSuchFieldException e) {
                System.out.println("sort is failed");
            }
        }
        return PageableUtil.getPageable(page, perPage, accounts);
    }

    private Message createMessageInstance(SendMailRequest mail) throws MessagingException {
        Properties props = emailSender.getJavaMailProperties();
        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(mail.getFrom(), mail.getFromPass());
                    }
                });

        InternetAddress[] myToList = InternetAddress.parse(mail.getTo());
        InternetAddress[] myBccList = InternetAddress.parse(mail.getBcc());
        InternetAddress[] myCcList = InternetAddress.parse(mail.getCc());

        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(mail.getFrom()));
        message.setRecipients(Message.RecipientType.TO, myToList);
        message.setRecipients(Message.RecipientType.BCC, myBccList);
        message.setRecipients(Message.RecipientType.CC, myCcList);
        message.setSubject(mail.getSubject());
        return message;
    }

    private Mail saveMail(SendMailRequest sendMailRequest) {
        Mail mail = new Mail();
        mail.setFrom(sendMailRequest.getFrom());
        mail.setTo(sendMailRequest.getTo());
        mail.setCc(sendMailRequest.getCc());
        mail.setBcc(sendMailRequest.getBcc());
        mail.setSubject(sendMailRequest.getSubject());
        mail.setContent(sendMailRequest.getContent());
        mail.setCreateOn(DateTimeUtils.getCurrentDateString(DateTimeConstants.YYYY_MM_DD_HYPHEN));
        mailRepository.save(mail);
        return mail;
    }
}
