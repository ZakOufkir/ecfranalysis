package com.ecfrProject.ecfranalysis.service;

import org.springframework.stereotype.Service;
import org.w3c.dom.*;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.*;

@Service
public class EcfrAnalysisService {

    public Map<String, Object> analyze(String xmlContent) {
        Map<String, Object> results = new HashMap<>();

        try {
            Document doc = DocumentBuilderFactory.newInstance()
                    .newDocumentBuilder()
                    .parse(new ByteArrayInputStream(xmlContent.getBytes(StandardCharsets.UTF_8)));

            doc.getDocumentElement().normalize();

            // Word count per <HEAD> (agency/part header)
            NodeList heads = doc.getElementsByTagName("HEAD");
            Map<String, Long> wordCount = new HashMap<>();

            for (int i = 0; i < heads.getLength(); i++) {
                String text = heads.item(i).getTextContent();
                text = text.replaceAll("[—,0-9.;]", " ").trim();
                    String[] elements = text.split("\\s+");
                    long count= Arrays.stream(elements).
                            filter(elem->elem.matches("[A-Za-z]+")).count();
                wordCount.put(text, count);

            }
            results.put("wordCountPerHead", wordCount);

            // Simple checksum (MD5 of XML content)
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] digest = md.digest(xmlContent.getBytes(StandardCharsets.UTF_8));
            String checksum = Base64.getEncoder().encodeToString(digest);
            results.put("checksum", checksum);

            // Custom metric: number of DIV5 parts
            int div5Count = doc.getElementsByTagName("DIV5").getLength();
            results.put("partCount", div5Count);

        } catch (Exception e) {
            results.put("error", e.getMessage());
        }

        return results;
    }
}

