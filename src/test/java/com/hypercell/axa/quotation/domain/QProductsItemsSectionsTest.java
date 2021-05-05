package com.hypercell.axa.quotation.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.hypercell.axa.quotation.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class QProductsItemsSectionsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(QProductsItemsSections.class);
        QProductsItemsSections qProductsItemsSections1 = new QProductsItemsSections();
        qProductsItemsSections1.setId(1L);
        QProductsItemsSections qProductsItemsSections2 = new QProductsItemsSections();
        qProductsItemsSections2.setId(qProductsItemsSections1.getId());
        assertThat(qProductsItemsSections1).isEqualTo(qProductsItemsSections2);
        qProductsItemsSections2.setId(2L);
        assertThat(qProductsItemsSections1).isNotEqualTo(qProductsItemsSections2);
        qProductsItemsSections1.setId(null);
        assertThat(qProductsItemsSections1).isNotEqualTo(qProductsItemsSections2);
    }
}
