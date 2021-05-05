package com.hypercell.axa.quotation.repository;

import com.hypercell.axa.quotation.domain.QProducts;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the QProducts entity.
 */
@SuppressWarnings("unused")
@Repository
public interface QProductsRepository extends JpaRepository<QProducts, Long> {}
