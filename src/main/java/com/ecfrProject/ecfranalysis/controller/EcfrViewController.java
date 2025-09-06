package com.ecfrProject.ecfranalysis.controller;
import com.ecfrProject.ecfranalysis.entity.EcfrDocument;
import com.ecfrProject.ecfranalysis.repository.EcfrDocumentRepository;
import com.ecfrProject.ecfranalysis.service.EcfrAnalysisService;
import com.ecfrProject.ecfranalysis.service.EcfrFetcherService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

@Controller
@RequestMapping("/ecfr")
public class EcfrViewController {

    private final EcfrFetcherService fetcherService;
    private final EcfrDocumentRepository repository;
    private final EcfrAnalysisService analysisService;

    public EcfrViewController(EcfrFetcherService fetcherService,
                              EcfrDocumentRepository repository,
        EcfrAnalysisService analysisService)
    {

        this.fetcherService = fetcherService;
        this.repository = repository;
        this.analysisService = analysisService;
    }

    // Show form to fetch XML
    @GetMapping("/fetch")
    public String showFetchForm() {
        return "fetch-form";
    }

    // Process form submission
    @PostMapping("/fetch")
    public String fetchAndSave(
            @RequestParam int title,
            @RequestParam String date,
            Model model)
    {
        EcfrDocument doc = fetcherService.fetchAndStore(title, LocalDate.parse(date));
        model.addAttribute("doc", doc);
        return "fetch-result";
    }

    // View stored document
    @GetMapping("/{title}/{date}")
    public String viewDocument(
            @PathVariable int title,
            @PathVariable String date,
            Model model)
    {

        repository.findByTitleAndDate(title, LocalDate.parse(date))
                .ifPresent(doc -> model.addAttribute("xmlContent", doc.getXmlContent()));
        return "view-document";
    }
    @GetMapping("/{title}/{date}/analysis")
    public String analyze(
            @PathVariable int title,
            @PathVariable String date,
            Model model) {

        return repository.findByTitleAndDate(title, LocalDate.parse(date))
                .map(doc -> {
                    Map<String, Object> results = analysisService.analyze(doc.getXmlContent());
                    model.addAttribute("results", results);
                    return "analysis";
                })
                .orElse("view-document");
    }
}

