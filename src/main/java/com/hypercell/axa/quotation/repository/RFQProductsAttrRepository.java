package com.hypercell.axa.quotation.repository;

import com.hypercell.axa.quotation.domain.RFQProductsAttr;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the RFQProductsAttr entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RFQProductsAttrRepository extends JpaRepository<RFQProductsAttr, Long> {}
