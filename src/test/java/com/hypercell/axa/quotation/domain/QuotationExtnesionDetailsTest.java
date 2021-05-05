package com.hypercell.axa.quotation.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.hypercell.axa.quotation.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class QuotationExtnesionDetailsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(QuotationExtnesionDetails.class);
        QuotationExtnesionDetails quotationExtnesionDetails1 = new QuotationExtnesionDetails();
        quotationExtnesionDetails1.setId(1L);
        QuotationExtnesionDetails quotationExtnesionDetails2 = new QuotationExtnesionDetails();
        quotationExtnesionDetails2.setId(quotationExtnesionDetails1.getId());
        assertThat(quotationExtnesionDetails1).isEqualTo(quotationExtnesionDetails2);
        quotationExtnesionDetails2.setId(2L);
        assertThat(quotationExtnesionDetails1).isNotEqualTo(quotationExtnesionDetails2);
        quotationExtnesionDetails1.setId(null);
        assertThat(quotationExtnesionDetails1).isNotEqualTo(quotationExtnesionDetails2);
    }
}
