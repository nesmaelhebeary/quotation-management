package com.hypercell.axa.quotation.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.hypercell.axa.quotation.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class RFQProductsExtensionsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RFQProductsExtensions.class);
        RFQProductsExtensions rFQProductsExtensions1 = new RFQProductsExtensions();
        rFQProductsExtensions1.setId(1L);
        RFQProductsExtensions rFQProductsExtensions2 = new RFQProductsExtensions();
        rFQProductsExtensions2.setId(rFQProductsExtensions1.getId());
        assertThat(rFQProductsExtensions1).isEqualTo(rFQProductsExtensions2);
        rFQProductsExtensions2.setId(2L);
        assertThat(rFQProductsExtensions1).isNotEqualTo(rFQProductsExtensions2);
        rFQProductsExtensions1.setId(null);
        assertThat(rFQProductsExtensions1).isNotEqualTo(rFQProductsExtensions2);
    }
}
