package com.hypercell.axa.quotation.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.hypercell.axa.quotation.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SiteSurveyDetailsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SiteSurveyDetails.class);
        SiteSurveyDetails siteSurveyDetails1 = new SiteSurveyDetails();
        siteSurveyDetails1.setId(1L);
        SiteSurveyDetails siteSurveyDetails2 = new SiteSurveyDetails();
        siteSurveyDetails2.setId(siteSurveyDetails1.getId());
        assertThat(siteSurveyDetails1).isEqualTo(siteSurveyDetails2);
        siteSurveyDetails2.setId(2L);
        assertThat(siteSurveyDetails1).isNotEqualTo(siteSurveyDetails2);
        siteSurveyDetails1.setId(null);
        assertThat(siteSurveyDetails1).isNotEqualTo(siteSurveyDetails2);
    }
}
