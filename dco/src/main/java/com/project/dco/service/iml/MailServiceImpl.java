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
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class MailServiceImpl implements MailService {

    @Autowired
    private JavaMailSenderImpl emailSender;

    @Autowired
    private MailRepository mailRepository;

    private static final String mailContentType = "text/html; charset=utf-8";

    // sending mail with default mail
    @Override
    public Mail sendMail(SendMailRequest sendMailRequest, MultipartFile template, MultipartFile[] files) throws MessagingException, IOException {
        String tempt = new String(template.getBytes());
        MimeMessage message = createMimeMessage(sendMailRequest, tempt, files);
        emailSender.send(message);
        return saveMail(sendMailRequest);
    }

    // sending mail with base64 file
    @Override
    public Mail sendMail(SendMailRequest sendMailRequest) throws MessagingException, IOException {
        String template = "";
        if (!StringUtils.isEmpty(sendMailRequest.getTemplate())) {
            byte[] decodedTempt = Base64.getDecoder().decode(sendMailRequest.getTemplate());
            template = new String(decodedTempt);
        }
        List<File> files = new ArrayList<>();
        if (!StringUtils.isEmpty(sendMailRequest.getAttachedFile()) && !StringUtils.isEmpty(sendMailRequest.getFileName())) {
            byte[] decodeAttachedFile = Base64.getDecoder().decode(sendMailRequest.getAttachedFile());
            String attachedFile = new String(decodeAttachedFile);
            File file = new File("D:/" + sendMailRequest.getFileName());
            file.createNewFile();

            BufferedOutputStream bof = new BufferedOutputStream(new FileOutputStream(file));
            bof.write(attachedFile.getBytes());
            bof.flush();
            bof.close();

            files.add(file);
        }
        MimeMessage message = createMimeMessage(sendMailRequest, template, files.stream().toArray());
        emailSender.send(message);
        return saveMail(sendMailRequest);
    }

    // sending mail with dynamic mail from api
    @Override
    public Mail sendMail(SendMailRequest sendMailRequest, MultipartFile[] template, MultipartFile[] files) throws MessagingException, IOException {
        Properties props = emailSender.getJavaMailProperties();
        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(sendMailRequest.getFrom(), sendMailRequest.getFromPass());
                    }
                });

        InternetAddress[] myToList = InternetAddress.parse(sendMailRequest.getTo());
        InternetAddress[] myBccList = InternetAddress.parse(sendMailRequest.getBcc());
        InternetAddress[] myCcList = InternetAddress.parse(sendMailRequest.getCc());

        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(sendMailRequest.getFrom()));
        message.setRecipients(Message.RecipientType.TO, myToList);
        message.setRecipients(Message.RecipientType.BCC, myBccList);
        message.setRecipients(Message.RecipientType.CC, myCcList);
        message.setSubject(sendMailRequest.getSubject());

        BodyPart messageBodyPart = new MimeBodyPart();
        StringBuilder content = new StringBuilder(sendMailRequest.getContent());
        if (template != null && template.length > 0) {
            for (MultipartFile tempt : template) {
                String temptName = tempt.getOriginalFilename();
                if (!StringUtils.isEmpty(temptName) && ".html".equals(temptName.substring(temptName.lastIndexOf(".")))) {
                    content.append("<br/>").append(new String(tempt.getBytes(), StandardCharsets.UTF_8));
                }
            }
        }
        messageBodyPart.setContent(content, mailContentType);

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
                e.printStackTrace();
            }
        }
        return PageableUtil.getPageable(page, perPage, accounts);
    }

    private MimeMessage createMimeMessage(SendMailRequest sendMailRequest, String template, Object[] files) throws MessagingException {
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setTo(sendMailRequest.getTo());
        if (!StringUtils.isEmpty(sendMailRequest.getCc())) {
            helper.setCc(sendMailRequest.getCc());
        }
        if (!StringUtils.isEmpty(sendMailRequest.getBcc())) {
            helper.setBcc(sendMailRequest.getBcc());
        }
        helper.setSubject(sendMailRequest.getSubject());
        helper.setText(sendMailRequest.getContent() + "<br/>" + template, true);
        if (files != null && files.length > 0) {
            for (Object file : files) {
                if (file instanceof MultipartFile) {
                    MultipartFile receivedFile = (MultipartFile) file;
                    helper.addAttachment(Objects.requireNonNull(receivedFile.getOriginalFilename()), receivedFile);
                }
                if (file instanceof File) {
                    File receivedFile = (File) file;
                    helper.addAttachment(receivedFile.getName(), receivedFile);
                }
            }
        }
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
