package com.project.dco;

import com.project.dco.dto.model.Claim;
import fr.opensagres.xdocreport.core.XDocReportException;
import fr.opensagres.xdocreport.template.TemplateEngineKind;
import fr.opensagres.xdocreport.template.formatter.FieldsMetadata;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

@SpringBootApplication
public class DcoApplication {

	public static void main(String[] args) throws XDocReportException, IOException {
		SpringApplication.run(DcoApplication.class, args);

		FieldsMetadata fieldsMetadata = new FieldsMetadata(TemplateEngineKind.Freemarker.name());
		fieldsMetadata.load("claim", Claim.class);
		File xmlFieldsFile = new File("project.fields.xml");
		fieldsMetadata.saveXML(new FileOutputStream(xmlFieldsFile), true);
	}

}
