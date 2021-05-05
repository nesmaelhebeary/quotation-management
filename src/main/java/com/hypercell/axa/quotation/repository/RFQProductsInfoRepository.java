package com.hypercell.axa.quotation.repository;

import com.hypercell.axa.quotation.domain.RFQProductsInfo;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the RFQProductsInfo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RFQProductsInfoRepository extends JpaRepository<RFQProductsInfo, Long> {}
