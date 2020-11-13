package com.project.dco.controller;

import com.project.dco.service.FileService;
import com.project.dco_common.api.AppResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(value = "/file")
@CrossOrigin
public class FileController {

    @Autowired
    private FileService fileService;

    @GetMapping("/open")
    public ResponseEntity<?> getFileAndView(@RequestParam String fileName) {
        HttpHeaders headers = new HttpHeaders();
        InputStreamResource res = fileService.getFile(fileName);
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=" + fileName);
        return AppResponseEntity.ok()
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .headers(headers)
                .body(res);
    }

    @PostMapping("/upload")
    public AppResponseEntity<?> uploadFile(@RequestParam("files") MultipartFile[] multipartFile) {
        fileService.uploadFile(multipartFile);
        return AppResponseEntity.withSuccess("");
    }
}
