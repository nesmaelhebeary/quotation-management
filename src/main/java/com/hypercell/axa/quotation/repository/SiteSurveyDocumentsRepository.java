package com.hypercell.axa.quotation.repository;

import com.hypercell.axa.quotation.domain.SiteSurveyDocuments;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the SiteSurveyDocuments entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SiteSurveyDocumentsRepository extends JpaRepository<SiteSurveyDocuments, Long> {}
