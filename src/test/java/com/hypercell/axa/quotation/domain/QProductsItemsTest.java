package com.hypercell.axa.quotation.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.hypercell.axa.quotation.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class QProductsItemsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(QProductsItems.class);
        QProductsItems qProductsItems1 = new QProductsItems();
        qProductsItems1.setId(1L);
        QProductsItems qProductsItems2 = new QProductsItems();
        qProductsItems2.setId(qProductsItems1.getId());
        assertThat(qProductsItems1).isEqualTo(qProductsItems2);
        qProductsItems2.setId(2L);
        assertThat(qProductsItems1).isNotEqualTo(qProductsItems2);
        qProductsItems1.setId(null);
        assertThat(qProductsItems1).isNotEqualTo(qProductsItems2);
    }
}
