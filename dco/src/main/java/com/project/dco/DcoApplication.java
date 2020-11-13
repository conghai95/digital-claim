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

		// 1) Create FieldsMetadata by setting Velocity as template engine
		FieldsMetadata fieldsMetadata = new FieldsMetadata(TemplateEngineKind.Freemarker.name());

		// 2) Load fields metadata from Java Class
		fieldsMetadata.load("claim", Claim.class);
		// Here load is called with true because model is a list of Developer.

		// 3) Generate XML fields in the file "project.fields.xml".
		// Extension *.fields.xml is very important to use it with MS Macro XDocReport.dotm
		// FieldsMetadata#saveXML is called with true to indent the XML.
		File xmlFieldsFile = new File("project.fields.xml");
		fieldsMetadata.saveXML(new FileOutputStream(xmlFieldsFile), true);
	}

}
