package com.project.dco.controller;

import com.project.dco.dto.request.CreateClaimRequest;
import com.project.dco.dto.request.SendMailRequest;
import com.project.dco.service.MailService;
import com.project.dco_common.api.AppResponseEntity;
import com.project.dco_common.api.HttpStatusResponse;
import com.project.dco_common.utils.JsonHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(value = "/mail")
public class MailController {

    @Autowired
    private MailService mailService;

    @PostMapping(value = "/send")
    public AppResponseEntity<?> sendMail(@RequestPart("sendMailRequest") String sendMailRequest, @RequestPart("files") MultipartFile[] multipartFile) {
        try {
            SendMailRequest request = JsonHelper.convertJson2Object(sendMailRequest, SendMailRequest.class);
            mailService.sendMail(request, multipartFile);
            return AppResponseEntity.withSuccess("Success");
        } catch (Exception e) {
            HttpStatusResponse status = new HttpStatusResponse();
            status.setCode("500");
            status.setHttp(500);
            status.setMessage("Failed");
            return AppResponseEntity.withError(status);
        }

    }
}
