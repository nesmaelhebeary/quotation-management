package com.hypercell.axa.quotation.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.hypercell.axa.quotation.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class QProductsInfoValueListTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(QProductsInfoValueList.class);
        QProductsInfoValueList qProductsInfoValueList1 = new QProductsInfoValueList();
        qProductsInfoValueList1.setId(1L);
        QProductsInfoValueList qProductsInfoValueList2 = new QProductsInfoValueList();
        qProductsInfoValueList2.setId(qProductsInfoValueList1.getId());
        assertThat(qProductsInfoValueList1).isEqualTo(qProductsInfoValueList2);
        qProductsInfoValueList2.setId(2L);
        assertThat(qProductsInfoValueList1).isNotEqualTo(qProductsInfoValueList2);
        qProductsInfoValueList1.setId(null);
        assertThat(qProductsInfoValueList1).isNotEqualTo(qProductsInfoValueList2);
    }
}
