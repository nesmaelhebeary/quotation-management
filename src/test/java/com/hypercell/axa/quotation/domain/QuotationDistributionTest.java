package com.hypercell.axa.quotation.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.hypercell.axa.quotation.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class QuotationDistributionTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(QuotationDistribution.class);
        QuotationDistribution quotationDistribution1 = new QuotationDistribution();
        quotationDistribution1.setId(1L);
        QuotationDistribution quotationDistribution2 = new QuotationDistribution();
        quotationDistribution2.setId(quotationDistribution1.getId());
        assertThat(quotationDistribution1).isEqualTo(quotationDistribution2);
        quotationDistribution2.setId(2L);
        assertThat(quotationDistribution1).isNotEqualTo(quotationDistribution2);
        quotationDistribution1.setId(null);
        assertThat(quotationDistribution1).isNotEqualTo(quotationDistribution2);
    }
}
