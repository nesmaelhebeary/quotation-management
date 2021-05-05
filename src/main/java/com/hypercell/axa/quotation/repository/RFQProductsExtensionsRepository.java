package com.hypercell.axa.quotation.repository;

import com.hypercell.axa.quotation.domain.RFQProductsExtensions;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the RFQProductsExtensions entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RFQProductsExtensionsRepository extends JpaRepository<RFQProductsExtensions, Long> {}
