package com.project.dco;

import com.project.dco.dto.request.CreateClaimRequest;
import fr.opensagres.xdocreport.core.XDocReportException;
import fr.opensagres.xdocreport.template.TemplateEngineKind;
import fr.opensagres.xdocreport.template.formatter.FieldsMetadata;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;

@SpringBootApplication
public class DcoApplication {

	public static void main(String[] args) throws XDocReportException, IOException {
		SpringApplication.run(DcoApplication.class, args);

		FieldsMetadata fieldsMetadata = new FieldsMetadata(TemplateEngineKind.Freemarker.name());

		fieldsMetadata.load("createClaimRequest", CreateClaimRequest.class);

		File xmlFieldsFile = new File("project.fields.xml");
		fieldsMetadata.saveXML(new FileOutputStream(xmlFieldsFile), true);

//		byte[] array = Files.readAllBytes(Paths.get("D:/template.html"));
		String encodedString = Base64.getEncoder().encodeToString("luong van cong hai".getBytes());
		System.out.println("encodedBase64String: " + encodedString);

//		byte[] decodedBytes = Base64.getDecoder().decode(encodedString);
//		String decodedString = new String(decodedBytes);
//
//		File file = new File("D:/share/music.txt");
//		file.createNewFile();
//		BufferedOutputStream bof = new BufferedOutputStream(new FileOutputStream(file));
//		bof.write(decodedString.getBytes());
//		System.out.println("decodedString: " + decodedString);
//
//		FileInputStream in = new FileInputStream("D:/share/music.txt");
//
//		in.close();
//		bof.flush();
//		bof.close();
	}

}
