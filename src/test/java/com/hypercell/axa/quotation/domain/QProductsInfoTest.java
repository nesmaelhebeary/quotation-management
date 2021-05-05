package com.hypercell.axa.quotation.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.hypercell.axa.quotation.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class QProductsInfoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(QProductsInfo.class);
        QProductsInfo qProductsInfo1 = new QProductsInfo();
        qProductsInfo1.setId(1L);
        QProductsInfo qProductsInfo2 = new QProductsInfo();
        qProductsInfo2.setId(qProductsInfo1.getId());
        assertThat(qProductsInfo1).isEqualTo(qProductsInfo2);
        qProductsInfo2.setId(2L);
        assertThat(qProductsInfo1).isNotEqualTo(qProductsInfo2);
        qProductsInfo1.setId(null);
        assertThat(qProductsInfo1).isNotEqualTo(qProductsInfo2);
    }
}
