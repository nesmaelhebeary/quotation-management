package com.hypercell.axa.quotation.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.hypercell.axa.quotation.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class QDocumentsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(QDocuments.class);
        QDocuments qDocuments1 = new QDocuments();
        qDocuments1.setId(1L);
        QDocuments qDocuments2 = new QDocuments();
        qDocuments2.setId(qDocuments1.getId());
        assertThat(qDocuments1).isEqualTo(qDocuments2);
        qDocuments2.setId(2L);
        assertThat(qDocuments1).isNotEqualTo(qDocuments2);
        qDocuments1.setId(null);
        assertThat(qDocuments1).isNotEqualTo(qDocuments2);
    }
}
