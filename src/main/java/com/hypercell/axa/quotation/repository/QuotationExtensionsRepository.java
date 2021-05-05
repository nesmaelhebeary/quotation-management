package com.hypercell.axa.quotation.repository;

import com.hypercell.axa.quotation.domain.QuotationExtensions;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the QuotationExtensions entity.
 */
@SuppressWarnings("unused")
@Repository
public interface QuotationExtensionsRepository extends JpaRepository<QuotationExtensions, Long> {}
