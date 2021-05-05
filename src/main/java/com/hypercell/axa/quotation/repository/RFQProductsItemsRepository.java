package com.hypercell.axa.quotation.repository;

import com.hypercell.axa.quotation.domain.RFQProductsItems;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the RFQProductsItems entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RFQProductsItemsRepository extends JpaRepository<RFQProductsItems, Long> {}
