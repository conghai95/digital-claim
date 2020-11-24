package com.project.dco;

import com.project.dco.dto.request.CreateClaimRequest;
import fr.opensagres.xdocreport.core.XDocReportException;
import fr.opensagres.xdocreport.template.TemplateEngineKind;
import fr.opensagres.xdocreport.template.formatter.FieldsMetadata;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.crypto.Cipher;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.KeyStore;
import java.security.MessageDigest;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.Certificate;
import java.util.Arrays;
import java.util.Base64;

@SpringBootApplication
public class DcoApplication {

    public static void main(String[] args) throws XDocReportException, IOException {
		SpringApplication.run(DcoApplication.class, args);
//
//		FieldsMetadata fieldsMetadata = new FieldsMetadata(TemplateEngineKind.Freemarker.name());
//
//		fieldsMetadata.load("createClaimRequest", CreateClaimRequest.class);
//
//		File xmlFieldsFile = new File("project.fields.xml");
//		fieldsMetadata.saveXML(new FileOutputStream(xmlFieldsFile), true);

		byte[] messageBytes = Files.readAllBytes(Paths.get("D:/share/file/abc.txt"));

        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] messageHash = md.digest(messageBytes);
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, getPrivateKey());
            byte[] digitalSignature = cipher.doFinal(messageHash);

            cipher.init(Cipher.DECRYPT_MODE, getPublicKey());
            byte[] decryptedMessageHash = cipher.doFinal(digitalSignature);

            System.out.println("check: " + Arrays.equals(decryptedMessageHash, messageHash));

        } catch (Exception e) {
            System.out.println("error" + e.getMessage());
        }


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

    private static PrivateKey getPrivateKey() {
        try {
            KeyStore keyStore = KeyStore.getInstance("PKCS12");
            keyStore.load(new FileInputStream("D:/share/file/serverKeystore.p12"), "123456".toCharArray());
            PrivateKey privateKey =
                    (PrivateKey) keyStore.getKey("senderKeyPair", "123456".toCharArray());
            System.out.println(Base64.getEncoder().encodeToString(privateKey.getEncoded()));
            return privateKey;
        } catch (Exception e) {
            return null;
        }
    }

    private static PublicKey getPublicKey() {
        try {
            KeyStore keyStore = KeyStore.getInstance("PKCS12");
            keyStore.load(new FileInputStream("D:/share/file/serverKeystore.p12"), "123456".toCharArray());
            Certificate certificate = keyStore.getCertificate("senderKeyPair");
            PublicKey publicKey = certificate.getPublicKey();
            return publicKey;
        } catch (Exception e) {
            return null;
        }
    }

}
