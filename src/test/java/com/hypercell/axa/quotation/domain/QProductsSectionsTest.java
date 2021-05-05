package com.hypercell.axa.quotation.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.hypercell.axa.quotation.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class QProductsSectionsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(QProductsSections.class);
        QProductsSections qProductsSections1 = new QProductsSections();
        qProductsSections1.setId(1L);
        QProductsSections qProductsSections2 = new QProductsSections();
        qProductsSections2.setId(qProductsSections1.getId());
        assertThat(qProductsSections1).isEqualTo(qProductsSections2);
        qProductsSections2.setId(2L);
        assertThat(qProductsSections1).isNotEqualTo(qProductsSections2);
        qProductsSections1.setId(null);
        assertThat(qProductsSections1).isNotEqualTo(qProductsSections2);
    }
}
