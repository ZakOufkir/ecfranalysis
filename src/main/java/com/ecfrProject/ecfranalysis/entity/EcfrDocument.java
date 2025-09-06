package com.ecfrProject.ecfranalysis.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "ecfr_documents")
@Getter
@Setter
public class EcfrDocument {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int title;

    private LocalDate date;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String xmlContent;

    // Constructors, getters, setters
    public EcfrDocument() {}

    public EcfrDocument(int title, LocalDate date, String xmlContent) {
        this.title = title;
        this.date = date;
        this.xmlContent = xmlContent;
    }

    // getters/setters ...
}
