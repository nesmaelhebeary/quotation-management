package com.hypercell.axa.quotation.repository;

import com.hypercell.axa.quotation.domain.QProductsInfoValueList;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the QProductsInfoValueList entity.
 */
@SuppressWarnings("unused")
@Repository
public interface QProductsInfoValueListRepository extends JpaRepository<QProductsInfoValueList, Long> {}
