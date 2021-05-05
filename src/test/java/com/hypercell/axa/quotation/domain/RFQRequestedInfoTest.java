package com.hypercell.axa.quotation.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.hypercell.axa.quotation.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class RFQRequestedInfoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RFQRequestedInfo.class);
        RFQRequestedInfo rFQRequestedInfo1 = new RFQRequestedInfo();
        rFQRequestedInfo1.setId(1L);
        RFQRequestedInfo rFQRequestedInfo2 = new RFQRequestedInfo();
        rFQRequestedInfo2.setId(rFQRequestedInfo1.getId());
        assertThat(rFQRequestedInfo1).isEqualTo(rFQRequestedInfo2);
        rFQRequestedInfo2.setId(2L);
        assertThat(rFQRequestedInfo1).isNotEqualTo(rFQRequestedInfo2);
        rFQRequestedInfo1.setId(null);
        assertThat(rFQRequestedInfo1).isNotEqualTo(rFQRequestedInfo2);
    }
}
