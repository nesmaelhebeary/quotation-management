package com.hypercell.axa.quotation.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.hypercell.axa.quotation.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class RFQProductsInfoValueListTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RFQProductsInfoValueList.class);
        RFQProductsInfoValueList rFQProductsInfoValueList1 = new RFQProductsInfoValueList();
        rFQProductsInfoValueList1.setId(1L);
        RFQProductsInfoValueList rFQProductsInfoValueList2 = new RFQProductsInfoValueList();
        rFQProductsInfoValueList2.setId(rFQProductsInfoValueList1.getId());
        assertThat(rFQProductsInfoValueList1).isEqualTo(rFQProductsInfoValueList2);
        rFQProductsInfoValueList2.setId(2L);
        assertThat(rFQProductsInfoValueList1).isNotEqualTo(rFQProductsInfoValueList2);
        rFQProductsInfoValueList1.setId(null);
        assertThat(rFQProductsInfoValueList1).isNotEqualTo(rFQProductsInfoValueList2);
    }
}
