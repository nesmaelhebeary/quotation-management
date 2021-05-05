package com.hypercell.axa.quotation.repository;

import com.hypercell.axa.quotation.domain.RFQRequestedInfo;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the RFQRequestedInfo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RFQRequestedInfoRepository extends JpaRepository<RFQRequestedInfo, Long> {}
