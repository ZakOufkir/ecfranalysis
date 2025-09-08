package com.ecfrProject.ecfranalysis.service;

import com.ecfrProject.ecfranalysis.entity.EcfrDocument;
import com.ecfrProject.ecfranalysis.repository.EcfrDocumentRepository;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
public class EcfrFetcherService {

    private final EcfrDocumentRepository repository;
    private final RestTemplate restTemplate = new RestTemplate();

    public EcfrFetcherService(EcfrDocumentRepository repository) {
        this.repository = repository;
    }

    public EcfrDocument fetchAndStore(Long title, LocalDate date)
    {

        String url = String.format(
                "https://www.ecfr.gov/api/versioner/v1/full/%s/title-%d.xml",
                date, title
        );

        ResponseEntity<String> response= restTemplate.getForEntity(url, String.class);
        String xmlContent = new String(response.getBody().getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
        EcfrDocument doc = new EcfrDocument(title, date, xmlContent);
        return repository.save(doc);
    }



}
