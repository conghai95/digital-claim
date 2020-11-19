package com.project.dco.dto.request;

import lombok.Data;

@Data
public class SendMailRequest {

    String to;
    String cc;
    String sub;
    String content;

}
