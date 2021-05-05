package com.hypercell.axa.quotation.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.hypercell.axa.quotation.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class RFQProductsItemsValuesTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RFQProductsItemsValues.class);
        RFQProductsItemsValues rFQProductsItemsValues1 = new RFQProductsItemsValues();
        rFQProductsItemsValues1.setId(1L);
        RFQProductsItemsValues rFQProductsItemsValues2 = new RFQProductsItemsValues();
        rFQProductsItemsValues2.setId(rFQProductsItemsValues1.getId());
        assertThat(rFQProductsItemsValues1).isEqualTo(rFQProductsItemsValues2);
        rFQProductsItemsValues2.setId(2L);
        assertThat(rFQProductsItemsValues1).isNotEqualTo(rFQProductsItemsValues2);
        rFQProductsItemsValues1.setId(null);
        assertThat(rFQProductsItemsValues1).isNotEqualTo(rFQProductsItemsValues2);
    }
}
