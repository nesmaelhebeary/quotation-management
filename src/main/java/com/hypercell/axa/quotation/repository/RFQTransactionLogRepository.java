package com.hypercell.axa.quotation.repository;

import com.hypercell.axa.quotation.domain.RFQTransactionLog;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the RFQTransactionLog entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RFQTransactionLogRepository extends JpaRepository<RFQTransactionLog, Long> {}
