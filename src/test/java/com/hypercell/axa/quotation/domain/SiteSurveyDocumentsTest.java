package com.hypercell.axa.quotation.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.hypercell.axa.quotation.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SiteSurveyDocumentsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SiteSurveyDocuments.class);
        SiteSurveyDocuments siteSurveyDocuments1 = new SiteSurveyDocuments();
        siteSurveyDocuments1.setId(1L);
        SiteSurveyDocuments siteSurveyDocuments2 = new SiteSurveyDocuments();
        siteSurveyDocuments2.setId(siteSurveyDocuments1.getId());
        assertThat(siteSurveyDocuments1).isEqualTo(siteSurveyDocuments2);
        siteSurveyDocuments2.setId(2L);
        assertThat(siteSurveyDocuments1).isNotEqualTo(siteSurveyDocuments2);
        siteSurveyDocuments1.setId(null);
        assertThat(siteSurveyDocuments1).isNotEqualTo(siteSurveyDocuments2);
    }
}
