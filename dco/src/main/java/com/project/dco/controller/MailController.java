package com.project.dco.controller;

import com.project.dco.dto.request.SendMailRequest;
import com.project.dco.service.MailService;
import com.project.dco_common.api.AppResponseEntity;
import com.project.dco_common.exception.ExceptionCustomize;
import com.project.dco_common.utils.JsonHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(value = "/mail")
public class MailController {

    @Autowired
    private MailService mailService;

    // sending mail with default mail
    @PostMapping(value = "/send")
    public AppResponseEntity<?> sendMail(@RequestPart("sendMailRequest") String sendMailRequest,
                                         @RequestPart(name = "template", required = false) MultipartFile[] template,
                                         @RequestPart(name = "files", required = false) MultipartFile[] multipartFile) {
        SendMailRequest request = JsonHelper.convertJson2Object(sendMailRequest, SendMailRequest.class);
        return AppResponseEntity.withSuccess(mailService.sendMail(request, template, multipartFile));
    }

    // sending mail with dynamic mail from api
    @PostMapping(value = "")
    public AppResponseEntity<?> mailTo(@RequestPart("mail") String mail,
                                       @RequestPart(name = "template", required = false) MultipartFile[] template,
                                       @RequestPart(name = "files", required = false) MultipartFile[] multipartFile) {
        SendMailRequest request = JsonHelper.convertJson2Object(mail, SendMailRequest.class);
        try {
            return AppResponseEntity.withSuccess(mailService.mailTo(request, template, multipartFile));
        } catch (Exception e) {
            throw new ExceptionCustomize();
        }
    }

    // sending mail with base64 file
    @PostMapping(value = "/sending")
    public AppResponseEntity<?> mailSending(@RequestBody SendMailRequest sendMailRequest) {
        return AppResponseEntity.withSuccess(mailService.sendMail(sendMailRequest));
    }

    @GetMapping("/list")
    public AppResponseEntity<?> getMailList(@RequestParam(required = false) int page,
                                            @RequestParam(required = false) int perPage,
                                            @RequestParam(required = false) String searchField,
                                            @RequestParam(required = false) String searchText,
                                            @RequestParam(required = false) String sortField,
                                            @RequestParam(required = false) String sortType,
                                            @RequestParam(required = false) String dateField,
                                            @RequestParam(required = false) String timeForm,
                                            @RequestParam(required = false) String timeTo) {
        return AppResponseEntity.withSuccess(mailService.getMailList(page, perPage, searchField, searchText, sortField, sortType, dateField, timeForm, timeTo));
    }

}
