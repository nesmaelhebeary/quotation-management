package com.hypercell.axa.quotation.repository;

import com.hypercell.axa.quotation.domain.RFQProductsInfoValueList;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the RFQProductsInfoValueList entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RFQProductsInfoValueListRepository extends JpaRepository<RFQProductsInfoValueList, Long> {}
