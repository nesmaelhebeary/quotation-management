package com.hypercell.axa.quotation.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.hypercell.axa.quotation.domain.enumeration.RFQAction;
import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A RFQTransactionLog.
 */
@Entity
@Table(name = "rfq_transaction_log")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class RFQTransactionLog implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "action")
    private RFQAction action;

    @Column(name = "action_date")
    private LocalDate actionDate;

    @Column(name = "created_by")
    private String createdBy;

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

    public RFQTransactionLog id(Long id) {
        this.id = id;
        return this;
    }

    public RFQAction getAction() {
        return this.action;
    }

    public RFQTransactionLog action(RFQAction action) {
        this.action = action;
        return this;
    }

    public void setAction(RFQAction action) {
        this.action = action;
    }

    public LocalDate getActionDate() {
        return this.actionDate;
    }

    public RFQTransactionLog actionDate(LocalDate actionDate) {
        this.actionDate = actionDate;
        return this;
    }

    public void setActionDate(LocalDate actionDate) {
        this.actionDate = actionDate;
    }

    public String getCreatedBy() {
        return this.createdBy;
    }

    public RFQTransactionLog createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public RequestForQuotation getRequestForQuotation() {
        return this.requestForQuotation;
    }

    public RFQTransactionLog requestForQuotation(RequestForQuotation requestForQuotation) {
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
        if (!(o instanceof RFQTransactionLog)) {
            return false;
        }
        return id != null && id.equals(((RFQTransactionLog) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RFQTransactionLog{" +
            "id=" + getId() +
            ", action='" + getAction() + "'" +
            ", actionDate='" + getActionDate() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            "}";
    }
}
