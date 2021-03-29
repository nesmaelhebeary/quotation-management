package com.hypercell.axa.quotation.repository;

import com.hypercell.axa.quotation.domain.RFQProducts;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the RFQProducts entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RFQProductsRepository extends JpaRepository<RFQProducts, Long> {}
