package com.hypercell.axa.quotation.repository;

import com.hypercell.axa.quotation.domain.RFQProductsItemsSections;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the RFQProductsItemsSections entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RFQProductsItemsSectionsRepository extends JpaRepository<RFQProductsItemsSections, Long> {}
