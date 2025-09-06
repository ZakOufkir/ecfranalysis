package com.ecfrProject.ecfranalysis.repository;

import com.ecfrProject.ecfranalysis.entity.EcfrDocument;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface EcfrDocumentRepository extends JpaRepository<EcfrDocument, Long> {
    Optional<EcfrDocument> findByTitleAndDate(int title, LocalDate date);
}
