package com.hypercell.axa.quotation.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.hypercell.axa.quotation.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class RFQProductsAttrTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RFQProductsAttr.class);
        RFQProductsAttr rFQProductsAttr1 = new RFQProductsAttr();
        rFQProductsAttr1.setId(1L);
        RFQProductsAttr rFQProductsAttr2 = new RFQProductsAttr();
        rFQProductsAttr2.setId(rFQProductsAttr1.getId());
        assertThat(rFQProductsAttr1).isEqualTo(rFQProductsAttr2);
        rFQProductsAttr2.setId(2L);
        assertThat(rFQProductsAttr1).isNotEqualTo(rFQProductsAttr2);
        rFQProductsAttr1.setId(null);
        assertThat(rFQProductsAttr1).isNotEqualTo(rFQProductsAttr2);
    }
}
