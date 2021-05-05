package com.hypercell.axa.quotation.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.hypercell.axa.quotation.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class RFQProductsItemsSectionsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RFQProductsItemsSections.class);
        RFQProductsItemsSections rFQProductsItemsSections1 = new RFQProductsItemsSections();
        rFQProductsItemsSections1.setId(1L);
        RFQProductsItemsSections rFQProductsItemsSections2 = new RFQProductsItemsSections();
        rFQProductsItemsSections2.setId(rFQProductsItemsSections1.getId());
        assertThat(rFQProductsItemsSections1).isEqualTo(rFQProductsItemsSections2);
        rFQProductsItemsSections2.setId(2L);
        assertThat(rFQProductsItemsSections1).isNotEqualTo(rFQProductsItemsSections2);
        rFQProductsItemsSections1.setId(null);
        assertThat(rFQProductsItemsSections1).isNotEqualTo(rFQProductsItemsSections2);
    }
}
