package com.hypercell.axa.quotation.repository;

import com.hypercell.axa.quotation.domain.QProductsItemsSections;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the QProductsItemsSections entity.
 */
@SuppressWarnings("unused")
@Repository
public interface QProductsItemsSectionsRepository extends JpaRepository<QProductsItemsSections, Long> {}
