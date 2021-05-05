package com.hypercell.axa.quotation.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.hypercell.axa.quotation.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class RFQProductsSectionsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RFQProductsSections.class);
        RFQProductsSections rFQProductsSections1 = new RFQProductsSections();
        rFQProductsSections1.setId(1L);
        RFQProductsSections rFQProductsSections2 = new RFQProductsSections();
        rFQProductsSections2.setId(rFQProductsSections1.getId());
        assertThat(rFQProductsSections1).isEqualTo(rFQProductsSections2);
        rFQProductsSections2.setId(2L);
        assertThat(rFQProductsSections1).isNotEqualTo(rFQProductsSections2);
        rFQProductsSections1.setId(null);
        assertThat(rFQProductsSections1).isNotEqualTo(rFQProductsSections2);
    }
}
