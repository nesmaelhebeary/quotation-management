package com.hypercell.axa.quotation.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.hypercell.axa.quotation.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class QProductsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(QProducts.class);
        QProducts qProducts1 = new QProducts();
        qProducts1.setId(1L);
        QProducts qProducts2 = new QProducts();
        qProducts2.setId(qProducts1.getId());
        assertThat(qProducts1).isEqualTo(qProducts2);
        qProducts2.setId(2L);
        assertThat(qProducts1).isNotEqualTo(qProducts2);
        qProducts1.setId(null);
        assertThat(qProducts1).isNotEqualTo(qProducts2);
    }
}
