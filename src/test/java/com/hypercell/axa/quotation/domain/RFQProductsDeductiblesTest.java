package com.hypercell.axa.quotation.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.hypercell.axa.quotation.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class RFQProductsDeductiblesTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RFQProductsDeductibles.class);
        RFQProductsDeductibles rFQProductsDeductibles1 = new RFQProductsDeductibles();
        rFQProductsDeductibles1.setId(1L);
        RFQProductsDeductibles rFQProductsDeductibles2 = new RFQProductsDeductibles();
        rFQProductsDeductibles2.setId(rFQProductsDeductibles1.getId());
        assertThat(rFQProductsDeductibles1).isEqualTo(rFQProductsDeductibles2);
        rFQProductsDeductibles2.setId(2L);
        assertThat(rFQProductsDeductibles1).isNotEqualTo(rFQProductsDeductibles2);
        rFQProductsDeductibles1.setId(null);
        assertThat(rFQProductsDeductibles1).isNotEqualTo(rFQProductsDeductibles2);
    }
}
