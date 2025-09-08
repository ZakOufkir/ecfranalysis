package com.ecfrProject.ecfranalysis.repository;

import com.ecfrProject.ecfranalysis.entity.EcfrDocument;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface EcfrDocumentRepository extends JpaRepository<EcfrDocument, Long> {
    Optional<EcfrDocument> findByTitleAndDate(int title, LocalDate date);



}
