package com.hypercell.axa.quotation.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.hypercell.axa.quotation.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class RFQDocumentsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RFQDocuments.class);
        RFQDocuments rFQDocuments1 = new RFQDocuments();
        rFQDocuments1.setId(1L);
        RFQDocuments rFQDocuments2 = new RFQDocuments();
        rFQDocuments2.setId(rFQDocuments1.getId());
        assertThat(rFQDocuments1).isEqualTo(rFQDocuments2);
        rFQDocuments2.setId(2L);
        assertThat(rFQDocuments1).isNotEqualTo(rFQDocuments2);
        rFQDocuments1.setId(null);
        assertThat(rFQDocuments1).isNotEqualTo(rFQDocuments2);
    }
}
