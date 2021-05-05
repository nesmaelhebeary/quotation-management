package com.hypercell.axa.quotation.repository;

import com.hypercell.axa.quotation.domain.QuotationExtnesionDetails;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the QuotationExtnesionDetails entity.
 */
@SuppressWarnings("unused")
@Repository
public interface QuotationExtnesionDetailsRepository extends JpaRepository<QuotationExtnesionDetails, Long> {}
