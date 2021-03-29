package com.hypercell.axa.quotation.repository;

import com.hypercell.axa.quotation.domain.RFQSectionsItems;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the RFQSectionsItems entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RFQSectionsItemsRepository extends JpaRepository<RFQSectionsItems, Long> {}
