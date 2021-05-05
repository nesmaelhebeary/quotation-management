package com.hypercell.axa.quotation.repository;

import com.hypercell.axa.quotation.domain.QProductsItems;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the QProductsItems entity.
 */
@SuppressWarnings("unused")
@Repository
public interface QProductsItemsRepository extends JpaRepository<QProductsItems, Long> {}
