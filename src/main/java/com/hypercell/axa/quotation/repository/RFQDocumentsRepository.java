package com.hypercell.axa.quotation.repository;

import com.hypercell.axa.quotation.domain.RFQDocuments;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the RFQDocuments entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RFQDocumentsRepository extends JpaRepository<RFQDocuments, Long> {}
