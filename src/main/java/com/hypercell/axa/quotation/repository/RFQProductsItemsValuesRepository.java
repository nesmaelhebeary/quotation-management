package com.hypercell.axa.quotation.repository;

import com.hypercell.axa.quotation.domain.RFQProductsItemsValues;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the RFQProductsItemsValues entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RFQProductsItemsValuesRepository extends JpaRepository<RFQProductsItemsValues, Long> {}
