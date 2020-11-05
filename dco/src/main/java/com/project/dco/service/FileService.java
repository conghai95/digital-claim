package com.project.dco.service;

import org.springframework.core.io.InputStreamResource;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {

    void uploadFile(MultipartFile[] files);

    InputStreamResource getFile(String fileName);
}
