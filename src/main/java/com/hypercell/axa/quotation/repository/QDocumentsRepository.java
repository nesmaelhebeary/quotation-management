package com.hypercell.axa.quotation.repository;

import com.hypercell.axa.quotation.domain.QDocuments;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the QDocuments entity.
 */
@SuppressWarnings("unused")
@Repository
public interface QDocumentsRepository extends JpaRepository<QDocuments, Long> {}
