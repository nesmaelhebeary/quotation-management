package com.hypercell.axa.quotation.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A SiteSurveyDetails.
 */
@Entity
@Table(name = "site_survey_details")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class SiteSurveyDetails implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "quotation_id")
    private Long quotationId;

    @Column(name = "requested_by")
    private String requestedBy;

    @Column(name = "request_date")
    private LocalDate requestDate;

    @Column(name = "response_date")
    private LocalDate responseDate;

    @Column(name = "nace_code")
    private String naceCode;

    @Column(name = "data_score_sheet_path")
    private String dataScoreSheetPath;

    @Column(name = "risk_survey_report_path")
    private String riskSurveyReportPath;

    @OneToMany(mappedBy = "siteSurveyDetails")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "siteSurveyDetails" }, allowSetters = true)
    private Set<SiteSurveyDocuments> siteSurveyDocuments = new HashSet<>();

    @JsonIgnoreProperties(
        value = { "siteSurveyDetails", "qDocuments", "qProducts", "qProductsInfos", "requestForQuotation" },
        allowSetters = true
    )
    @OneToOne(mappedBy = "siteSurveyDetails")
    private Quotation quotation;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SiteSurveyDetails id(Long id) {
        this.id = id;
        return this;
    }

    public Long getQuotationId() {
        return this.quotationId;
    }

    public SiteSurveyDetails quotationId(Long quotationId) {
        this.quotationId = quotationId;
        return this;
    }

    public void setQuotationId(Long quotationId) {
        this.quotationId = quotationId;
    }

    public String getRequestedBy() {
        return this.requestedBy;
    }

    public SiteSurveyDetails requestedBy(String requestedBy) {
        this.requestedBy = requestedBy;
        return this;
    }

    public void setRequestedBy(String requestedBy) {
        this.requestedBy = requestedBy;
    }

    public LocalDate getRequestDate() {
        return this.requestDate;
    }

    public SiteSurveyDetails requestDate(LocalDate requestDate) {
        this.requestDate = requestDate;
        return this;
    }

    public void setRequestDate(LocalDate requestDate) {
        this.requestDate = requestDate;
    }

    public LocalDate getResponseDate() {
        return this.responseDate;
    }

    public SiteSurveyDetails responseDate(LocalDate responseDate) {
        this.responseDate = responseDate;
        return this;
    }

    public void setResponseDate(LocalDate responseDate) {
        this.responseDate = responseDate;
    }

    public String getNaceCode() {
        return this.naceCode;
    }

    public SiteSurveyDetails naceCode(String naceCode) {
        this.naceCode = naceCode;
        return this;
    }

    public void setNaceCode(String naceCode) {
        this.naceCode = naceCode;
    }

    public String getDataScoreSheetPath() {
        return this.dataScoreSheetPath;
    }

    public SiteSurveyDetails dataScoreSheetPath(String dataScoreSheetPath) {
        this.dataScoreSheetPath = dataScoreSheetPath;
        return this;
    }

    public void setDataScoreSheetPath(String dataScoreSheetPath) {
        this.dataScoreSheetPath = dataScoreSheetPath;
    }

    public String getRiskSurveyReportPath() {
        return this.riskSurveyReportPath;
    }

    public SiteSurveyDetails riskSurveyReportPath(String riskSurveyReportPath) {
        this.riskSurveyReportPath = riskSurveyReportPath;
        return this;
    }

    public void setRiskSurveyReportPath(String riskSurveyReportPath) {
        this.riskSurveyReportPath = riskSurveyReportPath;
    }

    public Set<SiteSurveyDocuments> getSiteSurveyDocuments() {
        return this.siteSurveyDocuments;
    }

    public SiteSurveyDetails siteSurveyDocuments(Set<SiteSurveyDocuments> siteSurveyDocuments) {
        this.setSiteSurveyDocuments(siteSurveyDocuments);
        return this;
    }

    public SiteSurveyDetails addSiteSurveyDocuments(SiteSurveyDocuments siteSurveyDocuments) {
        this.siteSurveyDocuments.add(siteSurveyDocuments);
        siteSurveyDocuments.setSiteSurveyDetails(this);
        return this;
    }

    public SiteSurveyDetails removeSiteSurveyDocuments(SiteSurveyDocuments siteSurveyDocuments) {
        this.siteSurveyDocuments.remove(siteSurveyDocuments);
        siteSurveyDocuments.setSiteSurveyDetails(null);
        return this;
    }

    public void setSiteSurveyDocuments(Set<SiteSurveyDocuments> siteSurveyDocuments) {
        if (this.siteSurveyDocuments != null) {
            this.siteSurveyDocuments.forEach(i -> i.setSiteSurveyDetails(null));
        }
        if (siteSurveyDocuments != null) {
            siteSurveyDocuments.forEach(i -> i.setSiteSurveyDetails(this));
        }
        this.siteSurveyDocuments = siteSurveyDocuments;
    }

    public Quotation getQuotation() {
        return this.quotation;
    }

    public SiteSurveyDetails quotation(Quotation quotation) {
        this.setQuotation(quotation);
        return this;
    }

    public void setQuotation(Quotation quotation) {
        if (this.quotation != null) {
            this.quotation.setSiteSurveyDetails(null);
        }
        if (quotation != null) {
            quotation.setSiteSurveyDetails(this);
        }
        this.quotation = quotation;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SiteSurveyDetails)) {
            return false;
        }
        return id != null && id.equals(((SiteSurveyDetails) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SiteSurveyDetails{" +
            "id=" + getId() +
            ", quotationId=" + getQuotationId() +
            ", requestedBy='" + getRequestedBy() + "'" +
            ", requestDate='" + getRequestDate() + "'" +
            ", responseDate='" + getResponseDate() + "'" +
            ", naceCode='" + getNaceCode() + "'" +
            ", dataScoreSheetPath='" + getDataScoreSheetPath() + "'" +
            ", riskSurveyReportPath='" + getRiskSurveyReportPath() + "'" +
            "}";
    }
}
