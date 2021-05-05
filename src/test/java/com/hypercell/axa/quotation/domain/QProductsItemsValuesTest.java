package com.hypercell.axa.quotation.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.hypercell.axa.quotation.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class QProductsItemsValuesTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(QProductsItemsValues.class);
        QProductsItemsValues qProductsItemsValues1 = new QProductsItemsValues();
        qProductsItemsValues1.setId(1L);
        QProductsItemsValues qProductsItemsValues2 = new QProductsItemsValues();
        qProductsItemsValues2.setId(qProductsItemsValues1.getId());
        assertThat(qProductsItemsValues1).isEqualTo(qProductsItemsValues2);
        qProductsItemsValues2.setId(2L);
        assertThat(qProductsItemsValues1).isNotEqualTo(qProductsItemsValues2);
        qProductsItemsValues1.setId(null);
        assertThat(qProductsItemsValues1).isNotEqualTo(qProductsItemsValues2);
    }
}
