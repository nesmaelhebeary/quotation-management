package com.hypercell.axa.quotation.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.hypercell.axa.quotation.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class RFQClaimsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RFQClaims.class);
        RFQClaims rFQClaims1 = new RFQClaims();
        rFQClaims1.setId(1L);
        RFQClaims rFQClaims2 = new RFQClaims();
        rFQClaims2.setId(rFQClaims1.getId());
        assertThat(rFQClaims1).isEqualTo(rFQClaims2);
        rFQClaims2.setId(2L);
        assertThat(rFQClaims1).isNotEqualTo(rFQClaims2);
        rFQClaims1.setId(null);
        assertThat(rFQClaims1).isNotEqualTo(rFQClaims2);
    }
}
