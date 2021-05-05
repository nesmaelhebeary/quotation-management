package com.hypercell.axa.quotation.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A SiteSurveyDocuments.
 */
@Entity
@Table(name = "site_survey_documents")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class SiteSurveyDocuments implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "survey_id")
    private Long surveyId;

    @Column(name = "document_path")
    private String documentPath;

    @Column(name = "document_name")
    private String documentName;

    @ManyToOne
    @JsonIgnoreProperties(value = { "siteSurveyDocuments", "quotation" }, allowSetters = true)
    private SiteSurveyDetails siteSurveyDetails;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SiteSurveyDocuments id(Long id) {
        this.id = id;
        return this;
    }

    public Long getSurveyId() {
        return this.surveyId;
    }

    public SiteSurveyDocuments surveyId(Long surveyId) {
        this.surveyId = surveyId;
        return this;
    }

    public void setSurveyId(Long surveyId) {
        this.surveyId = surveyId;
    }

    public String getDocumentPath() {
        return this.documentPath;
    }

    public SiteSurveyDocuments documentPath(String documentPath) {
        this.documentPath = documentPath;
        return this;
    }

    public void setDocumentPath(String documentPath) {
        this.documentPath = documentPath;
    }

    public String getDocumentName() {
        return this.documentName;
    }

    public SiteSurveyDocuments documentName(String documentName) {
        this.documentName = documentName;
        return this;
    }

    public void setDocumentName(String documentName) {
        this.documentName = documentName;
    }

    public SiteSurveyDetails getSiteSurveyDetails() {
        return this.siteSurveyDetails;
    }

    public SiteSurveyDocuments siteSurveyDetails(SiteSurveyDetails siteSurveyDetails) {
        this.setSiteSurveyDetails(siteSurveyDetails);
        return this;
    }

    public void setSiteSurveyDetails(SiteSurveyDetails siteSurveyDetails) {
        this.siteSurveyDetails = siteSurveyDetails;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SiteSurveyDocuments)) {
            return false;
        }
        return id != null && id.equals(((SiteSurveyDocuments) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SiteSurveyDocuments{" +
            "id=" + getId() +
            ", surveyId=" + getSurveyId() +
            ", documentPath='" + getDocumentPath() + "'" +
            ", documentName='" + getDocumentName() + "'" +
            "}";
    }
}
