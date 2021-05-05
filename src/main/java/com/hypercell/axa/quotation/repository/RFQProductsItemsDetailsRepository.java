package com.hypercell.axa.quotation.repository;

import com.hypercell.axa.quotation.domain.RFQProductsItemsDetails;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the RFQProductsItemsDetails entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RFQProductsItemsDetailsRepository extends JpaRepository<RFQProductsItemsDetails, Long> {}
