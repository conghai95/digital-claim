package com.project.dco.service.iml;

import com.project.dco.dto.model.Mail;
import com.project.dco.dto.request.SendMailRequest;
import com.project.dco.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

@Service
public class MailServiceImpl implements MailService {

    @Autowired
    private JavaMailSender emailSender;

    private Properties props;

    @Override
    public void sendMail(SendMailRequest sendMailRequest, MultipartFile[] files) throws MessagingException {
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setTo(sendMailRequest.getTo());
        helper.setSubject(sendMailRequest.getSub());
        helper.setText(sendMailRequest.getContent());

        if (files != null && files.length > 0) {
            for (MultipartFile multipartFile : files) {
                helper.addAttachment(multipartFile.getOriginalFilename(), multipartFile);
            }
        }
        emailSender.send(message);
    }

    @Override
    public void sendMail(Mail mail, MultipartFile[] files) throws MessagingException {
        String username = mail.getMailUserName();
        String password = mail.getMailPassword();
        String host = "";
        switch (mail.getDomain()) {
            case "gmail.com":
                host = "smtp.gmail.com";
                break;
            case "yahoo.com":
                host = "smtp.mail.yahoo.com";
                break;
            case "rediffmail.com":
                host = "smtp.rediffmail.com";
                break;
            default:
                host = "smtp.1and1.com";
                break;
        }
        props.put("mail.smtp.host", host);

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        Message message = new MimeMessage(session);
        InternetAddress[] myToList = InternetAddress.parse(mail.getTo());
        InternetAddress[] myBccList = InternetAddress.parse(mail.getBcc());
        InternetAddress[] myCcList = InternetAddress.parse(mail.getCc());

        message.setFrom(new InternetAddress(mail.getMailUserName()));
        message.setRecipients(Message.RecipientType.TO, myToList);
        message.setRecipients(Message.RecipientType.BCC, myBccList);
        message.setRecipients(Message.RecipientType.CC, myCcList);
        message.setSubject(mail.getSub());

        BodyPart messageBodyPart = new MimeBodyPart();
        messageBodyPart.setContent(mail.getContent(), "testing...");

        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(messageBodyPart);

        if (files != null && files.length > 0) {
            for (MultipartFile filePath : files) {
                MimeBodyPart attachPart = new MimeBodyPart();
                try {
                    attachPart.setContent(filePath.getBytes(), filePath.getContentType());
                    attachPart.setFileName(filePath.getOriginalFilename());
                    attachPart.setDisposition(Part.ATTACHMENT);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                multipart.addBodyPart(attachPart);
            }
        }
        message.setContent(multipart);
        Transport.send(message);

    }

    @Override
    public List<?> getHistoryByEmail(String email) {
        return null;
    }
}
