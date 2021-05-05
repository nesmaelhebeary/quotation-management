package com.hypercell.axa.quotation.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A QDocuments.
 */
@Entity
@Table(name = "q_documents")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class QDocuments implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "quotation_id")
    private Long quotationId;

    @Column(name = "document_path")
    private String documentPath;

    @Column(name = "document_name")
    private String documentName;

    @Column(name = "upload_date")
    private LocalDate uploadDate;

    @ManyToOne
    @JsonIgnoreProperties(
        value = { "siteSurveyDetails", "qDocuments", "qProducts", "qProductsInfos", "requestForQuotation" },
        allowSetters = true
    )
    private Quotation quotation;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public QDocuments id(Long id) {
        this.id = id;
        return this;
    }

    public Long getQuotationId() {
        return this.quotationId;
    }

    public QDocuments quotationId(Long quotationId) {
        this.quotationId = quotationId;
        return this;
    }

    public void setQuotationId(Long quotationId) {
        this.quotationId = quotationId;
    }

    public String getDocumentPath() {
        return this.documentPath;
    }

    public QDocuments documentPath(String documentPath) {
        this.documentPath = documentPath;
        return this;
    }

    public void setDocumentPath(String documentPath) {
        this.documentPath = documentPath;
    }

    public String getDocumentName() {
        return this.documentName;
    }

    public QDocuments documentName(String documentName) {
        this.documentName = documentName;
        return this;
    }

    public void setDocumentName(String documentName) {
        this.documentName = documentName;
    }

    public LocalDate getUploadDate() {
        return this.uploadDate;
    }

    public QDocuments uploadDate(LocalDate uploadDate) {
        this.uploadDate = uploadDate;
        return this;
    }

    public void setUploadDate(LocalDate uploadDate) {
        this.uploadDate = uploadDate;
    }

    public Quotation getQuotation() {
        return this.quotation;
    }

    public QDocuments quotation(Quotation quotation) {
        this.setQuotation(quotation);
        return this;
    }

    public void setQuotation(Quotation quotation) {
        this.quotation = quotation;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof QDocuments)) {
            return false;
        }
        return id != null && id.equals(((QDocuments) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "QDocuments{" +
            "id=" + getId() +
            ", quotationId=" + getQuotationId() +
            ", documentPath='" + getDocumentPath() + "'" +
            ", documentName='" + getDocumentName() + "'" +
            ", uploadDate='" + getUploadDate() + "'" +
            "}";
    }
}
