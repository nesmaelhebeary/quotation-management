package com.hypercell.axa.quotation.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A RFQDocuments.
 */
@Entity
@Table(name = "rfq_documents")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class RFQDocuments implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "document_path")
    private String documentPath;

    @Column(name = "document_name")
    private String documentName;

    @Column(name = "upload_date")
    private LocalDate uploadDate;

    @ManyToOne
    @JsonIgnoreProperties(
        value = { "quotations", "rFQTransactionLogs", "rFQRequestedInfos", "rFQDocuments", "rFQProducts", "rFQProductsInfos" },
        allowSetters = true
    )
    private RequestForQuotation requestForQuotation;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public RFQDocuments id(Long id) {
        this.id = id;
        return this;
    }

    public String getDocumentPath() {
        return this.documentPath;
    }

    public RFQDocuments documentPath(String documentPath) {
        this.documentPath = documentPath;
        return this;
    }

    public void setDocumentPath(String documentPath) {
        this.documentPath = documentPath;
    }

    public String getDocumentName() {
        return this.documentName;
    }

    public RFQDocuments documentName(String documentName) {
        this.documentName = documentName;
        return this;
    }

    public void setDocumentName(String documentName) {
        this.documentName = documentName;
    }

    public LocalDate getUploadDate() {
        return this.uploadDate;
    }

    public RFQDocuments uploadDate(LocalDate uploadDate) {
        this.uploadDate = uploadDate;
        return this;
    }

    public void setUploadDate(LocalDate uploadDate) {
        this.uploadDate = uploadDate;
    }

    public RequestForQuotation getRequestForQuotation() {
        return this.requestForQuotation;
    }

    public RFQDocuments requestForQuotation(RequestForQuotation requestForQuotation) {
        this.setRequestForQuotation(requestForQuotation);
        return this;
    }

    public void setRequestForQuotation(RequestForQuotation requestForQuotation) {
        this.requestForQuotation = requestForQuotation;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RFQDocuments)) {
            return false;
        }
        return id != null && id.equals(((RFQDocuments) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RFQDocuments{" +
            "id=" + getId() +
            ", documentPath='" + getDocumentPath() + "'" +
            ", documentName='" + getDocumentName() + "'" +
            ", uploadDate='" + getUploadDate() + "'" +
            "}";
    }
}
