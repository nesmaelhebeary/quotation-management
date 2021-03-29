package com.hypercell.axa.quotation.repository;

import com.hypercell.axa.quotation.domain.RFQClaims;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the RFQClaims entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RFQClaimsRepository extends JpaRepository<RFQClaims, Long> {}
