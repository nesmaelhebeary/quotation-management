package com.hypercell.axa.quotation.repository;

import com.hypercell.axa.quotation.domain.RequestForQuotation;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the RequestForQuotation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RequestForQuotationRepository extends JpaRepository<RequestForQuotation, Long> {}
