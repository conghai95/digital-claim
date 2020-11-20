package com.project.dco.service;

import fr.opensagres.xdocreport.core.XDocReportException;
import fr.opensagres.xdocreport.template.TemplateEngineKind;
import org.springframework.core.io.InputStreamResource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

public interface FileService {

    void uploadFile(MultipartFile[] files);

    InputStreamResource getFile(String fileName);

    byte[] mergeAndGenerateOutput(String filName, TemplateEngineKind templateEngineKind, Map<String, Object> nonImageVariableMap) throws IOException, XDocReportException;
}
