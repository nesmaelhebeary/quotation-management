package com.hypercell.axa.quotation.repository;

import com.hypercell.axa.quotation.domain.RFQProductsDeductibles;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the RFQProductsDeductibles entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RFQProductsDeductiblesRepository extends JpaRepository<RFQProductsDeductibles, Long> {}
