package com.hypercell.axa.quotation.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.hypercell.axa.quotation.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class RFQProductsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RFQProducts.class);
        RFQProducts rFQProducts1 = new RFQProducts();
        rFQProducts1.setId(1L);
        RFQProducts rFQProducts2 = new RFQProducts();
        rFQProducts2.setId(rFQProducts1.getId());
        assertThat(rFQProducts1).isEqualTo(rFQProducts2);
        rFQProducts2.setId(2L);
        assertThat(rFQProducts1).isNotEqualTo(rFQProducts2);
        rFQProducts1.setId(null);
        assertThat(rFQProducts1).isNotEqualTo(rFQProducts2);
    }
}
