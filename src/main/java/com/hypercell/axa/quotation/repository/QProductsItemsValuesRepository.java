package com.hypercell.axa.quotation.repository;

import com.hypercell.axa.quotation.domain.QProductsItemsValues;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the QProductsItemsValues entity.
 */
@SuppressWarnings("unused")
@Repository
public interface QProductsItemsValuesRepository extends JpaRepository<QProductsItemsValues, Long> {}
