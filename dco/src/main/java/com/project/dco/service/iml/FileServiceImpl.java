package com.project.dco.service.iml;

import com.project.dco.service.FileService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class FileServiceImpl implements FileService {

    @Value("${file.localPath}")
    private String filePath;

    List<File> fileUploaded = new ArrayList<>();

    @Override
    public void uploadFile(MultipartFile[] files) {
        File fileRootDir = createFolder();
        try {
            for (MultipartFile multipartFile : files) {
                File file = new File(fileRootDir.getAbsolutePath() + "/" + multipartFile.getOriginalFilename());
                BufferedOutputStream bStream = new BufferedOutputStream(new FileOutputStream(file));
                bStream.write(multipartFile.getBytes());
                bStream.close();
                fileUploaded.add(file);
            }
        } catch (IOException e) {
        }
    }

    @Override
    public InputStreamResource getFile(String fileName) {
        if(!isPdfFile(fileName)) {
            return null;
        }
        File fileRootDir = createFolder();
        File file = new File(fileRootDir.getAbsolutePath() + "/" + fileName);
        if (file.exists()) {
            try {
                InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
                return resource;
            } catch (FileNotFoundException ex) {
            }
        }
        return null;
    }

    private File createFolder() {
        File fileRootDir = new File(filePath);
        if (!fileRootDir.exists()) {
            fileRootDir.mkdirs();
        }
        return fileRootDir;
    }

    private boolean isPdfFile(String fileName) {
        String extension = fileName.substring(fileName.lastIndexOf(".") + 1);
        if ("pdf".equals(extension)) {
            return true;
        }
        return false;
    }
}
