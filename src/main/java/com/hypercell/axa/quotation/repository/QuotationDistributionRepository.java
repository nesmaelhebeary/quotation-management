package com.hypercell.axa.quotation.repository;

import com.hypercell.axa.quotation.domain.QuotationDistribution;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the QuotationDistribution entity.
 */
@SuppressWarnings("unused")
@Repository
public interface QuotationDistributionRepository extends JpaRepository<QuotationDistribution, Long> {}
