package com.hypercell.axa.quotation.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.hypercell.axa.quotation.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class RFQTransactionLogTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RFQTransactionLog.class);
        RFQTransactionLog rFQTransactionLog1 = new RFQTransactionLog();
        rFQTransactionLog1.setId(1L);
        RFQTransactionLog rFQTransactionLog2 = new RFQTransactionLog();
        rFQTransactionLog2.setId(rFQTransactionLog1.getId());
        assertThat(rFQTransactionLog1).isEqualTo(rFQTransactionLog2);
        rFQTransactionLog2.setId(2L);
        assertThat(rFQTransactionLog1).isNotEqualTo(rFQTransactionLog2);
        rFQTransactionLog1.setId(null);
        assertThat(rFQTransactionLog1).isNotEqualTo(rFQTransactionLog2);
    }
}
