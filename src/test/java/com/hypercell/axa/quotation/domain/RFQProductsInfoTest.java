package com.hypercell.axa.quotation.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.hypercell.axa.quotation.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class RFQProductsInfoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RFQProductsInfo.class);
        RFQProductsInfo rFQProductsInfo1 = new RFQProductsInfo();
        rFQProductsInfo1.setId(1L);
        RFQProductsInfo rFQProductsInfo2 = new RFQProductsInfo();
        rFQProductsInfo2.setId(rFQProductsInfo1.getId());
        assertThat(rFQProductsInfo1).isEqualTo(rFQProductsInfo2);
        rFQProductsInfo2.setId(2L);
        assertThat(rFQProductsInfo1).isNotEqualTo(rFQProductsInfo2);
        rFQProductsInfo1.setId(null);
        assertThat(rFQProductsInfo1).isNotEqualTo(rFQProductsInfo2);
    }
}
