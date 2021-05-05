package com.hypercell.axa.quotation.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.hypercell.axa.quotation.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class RFQProductsItemsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RFQProductsItems.class);
        RFQProductsItems rFQProductsItems1 = new RFQProductsItems();
        rFQProductsItems1.setId(1L);
        RFQProductsItems rFQProductsItems2 = new RFQProductsItems();
        rFQProductsItems2.setId(rFQProductsItems1.getId());
        assertThat(rFQProductsItems1).isEqualTo(rFQProductsItems2);
        rFQProductsItems2.setId(2L);
        assertThat(rFQProductsItems1).isNotEqualTo(rFQProductsItems2);
        rFQProductsItems1.setId(null);
        assertThat(rFQProductsItems1).isNotEqualTo(rFQProductsItems2);
    }
}
