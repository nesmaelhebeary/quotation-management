package com.hypercell.axa.quotation.repository;

import com.hypercell.axa.quotation.domain.QProductsInfo;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the QProductsInfo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface QProductsInfoRepository extends JpaRepository<QProductsInfo, Long> {}
