package com.hypercell.axa.quotation.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.hypercell.axa.quotation.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class QuotationExtensionsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(QuotationExtensions.class);
        QuotationExtensions quotationExtensions1 = new QuotationExtensions();
        quotationExtensions1.setId(1L);
        QuotationExtensions quotationExtensions2 = new QuotationExtensions();
        quotationExtensions2.setId(quotationExtensions1.getId());
        assertThat(quotationExtensions1).isEqualTo(quotationExtensions2);
        quotationExtensions2.setId(2L);
        assertThat(quotationExtensions1).isNotEqualTo(quotationExtensions2);
        quotationExtensions1.setId(null);
        assertThat(quotationExtensions1).isNotEqualTo(quotationExtensions2);
    }
}
