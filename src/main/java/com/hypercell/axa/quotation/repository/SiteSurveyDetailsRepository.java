package com.hypercell.axa.quotation.repository;

import com.hypercell.axa.quotation.domain.SiteSurveyDetails;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the SiteSurveyDetails entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SiteSurveyDetailsRepository extends JpaRepository<SiteSurveyDetails, Long> {}
