package com.hypercell.axa.quotation.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.hypercell.axa.quotation.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class RFQProductsItemsDetailsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RFQProductsItemsDetails.class);
        RFQProductsItemsDetails rFQProductsItemsDetails1 = new RFQProductsItemsDetails();
        rFQProductsItemsDetails1.setId(1L);
        RFQProductsItemsDetails rFQProductsItemsDetails2 = new RFQProductsItemsDetails();
        rFQProductsItemsDetails2.setId(rFQProductsItemsDetails1.getId());
        assertThat(rFQProductsItemsDetails1).isEqualTo(rFQProductsItemsDetails2);
        rFQProductsItemsDetails2.setId(2L);
        assertThat(rFQProductsItemsDetails1).isNotEqualTo(rFQProductsItemsDetails2);
        rFQProductsItemsDetails1.setId(null);
        assertThat(rFQProductsItemsDetails1).isNotEqualTo(rFQProductsItemsDetails2);
    }
}
