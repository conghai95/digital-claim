package com.project.dco.service.iml;

import com.project.dco.service.FileService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import fr.opensagres.xdocreport.core.XDocReportException;
import fr.opensagres.xdocreport.core.io.internal.ByteArrayOutputStream;
import fr.opensagres.xdocreport.document.IXDocReport;
import fr.opensagres.xdocreport.document.registry.XDocReportRegistry;
import fr.opensagres.xdocreport.template.IContext;
import fr.opensagres.xdocreport.template.TemplateEngineKind;

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
//        if (!isPdfFile(fileName)) {
//            return null;
//        }
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

    @Override
    public byte[] mergeAndGenerateOutput(String filName, TemplateEngineKind templateEngineKind, Map<String, Object> nonImageVariableMap) throws IOException, XDocReportException {
        InputStream inputStream = new File(filePath + "/" + filName).toURI().toURL().openStream();
        IXDocReport xDocReport = loadDocumentAsIDocxReport(inputStream, templateEngineKind);
        IContext context = replaceVariablesInTemplateOtherThanImages(xDocReport, nonImageVariableMap);
        byte[] mergedOutput = generateMergedOutput(xDocReport, context);
        FileOutputStream os = new FileOutputStream(filePath + "/output/" + filName);
        os.write(mergedOutput);
        inputStream.close();
        os.flush();
        os.close();
        return mergedOutput;
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

    private IXDocReport loadDocumentAsIDocxReport(InputStream documentTemplateAsStream, TemplateEngineKind freemarkerOrVelocityTemplateKind) throws IOException, XDocReportException {
        IXDocReport xDocReport = XDocReportRegistry.getRegistry().loadReport(documentTemplateAsStream, freemarkerOrVelocityTemplateKind);
        return xDocReport;
    }

    private IContext replaceVariablesInTemplateOtherThanImages(IXDocReport report, Map<String, Object> variablesToBeReplaced) throws XDocReportException {
        IContext context = report.createContext();
        for (Map.Entry<String, Object> variable : variablesToBeReplaced.entrySet()) {
            context.put(variable.getKey(), variable.getValue());
        }
        return context;
    }

    private byte[] generateMergedOutput(IXDocReport report, IContext context) throws XDocReportException, IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        report.process(context, outputStream);
        return outputStream.toByteArray();
    }
}
