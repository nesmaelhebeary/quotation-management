package com.hypercell.axa.quotation.repository;

import com.hypercell.axa.quotation.domain.RFQProductsSections;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the RFQProductsSections entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RFQProductsSectionsRepository extends JpaRepository<RFQProductsSections, Long> {}
