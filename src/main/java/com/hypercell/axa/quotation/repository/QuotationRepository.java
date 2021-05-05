package com.hypercell.axa.quotation.repository;

import com.hypercell.axa.quotation.domain.Quotation;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Quotation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface QuotationRepository extends JpaRepository<Quotation, Long> {}
