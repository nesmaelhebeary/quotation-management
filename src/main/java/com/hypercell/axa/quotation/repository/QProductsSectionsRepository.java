package com.hypercell.axa.quotation.repository;

import com.hypercell.axa.quotation.domain.QProductsSections;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the QProductsSections entity.
 */
@SuppressWarnings("unused")
@Repository
public interface QProductsSectionsRepository extends JpaRepository<QProductsSections, Long> {}
