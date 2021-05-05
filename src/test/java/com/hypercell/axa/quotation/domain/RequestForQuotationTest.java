package com.hypercell.axa.quotation.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.hypercell.axa.quotation.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class RequestForQuotationTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RequestForQuotation.class);
        RequestForQuotation requestForQuotation1 = new RequestForQuotation();
        requestForQuotation1.setId(1L);
        RequestForQuotation requestForQuotation2 = new RequestForQuotation();
        requestForQuotation2.setId(requestForQuotation1.getId());
        assertThat(requestForQuotation1).isEqualTo(requestForQuotation2);
        requestForQuotation2.setId(2L);
        assertThat(requestForQuotation1).isNotEqualTo(requestForQuotation2);
        requestForQuotation1.setId(null);
        assertThat(requestForQuotation1).isNotEqualTo(requestForQuotation2);
    }
}
