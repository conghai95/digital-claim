package com.project.dco.dto.request;

import com.project.dco.dto.common.AttachedFileModel;
import lombok.Data;

import java.util.List;

@Data
public class SendMailRequest {

    // using for sending mail by special mail which from api
    String from;
    String fromPass;
    // using for sending mail by default mail which from configuration
    String to;
    String cc;
    String bcc;
    String subject;
    String content;
    List<String> templates;
    List<AttachedFileModel> attachedFiles;
}
