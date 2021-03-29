package com.hypercell.axa.quotation.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A RFQClaims.
 */
@Entity
@Table(name = "rfq_claims")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class RFQClaims implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "rfq_id")
    private Long rfqId;

    @Column(name = "claim_year")
    private Long claimYear;

    @Column(name = "paid_amount")
    private Double paidAmount;

    @Column(name = "outstanding_amount")
    private Double outstandingAmount;

    @ManyToOne
    @JsonIgnoreProperties(value = { "rFQClaims", "rFQDocuments", "rFQProducts" }, allowSetters = true)
    private RequestForQuotation requestForQuotation;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public RFQClaims id(Long id) {
        this.id = id;
        return this;
    }

    public Long getRfqId() {
        return this.rfqId;
    }

    public RFQClaims rfqId(Long rfqId) {
        this.rfqId = rfqId;
        return this;
    }

    public void setRfqId(Long rfqId) {
        this.rfqId = rfqId;
    }

    public Long getClaimYear() {
        return this.claimYear;
    }

    public RFQClaims claimYear(Long claimYear) {
        this.claimYear = claimYear;
        return this;
    }

    public void setClaimYear(Long claimYear) {
        this.claimYear = claimYear;
    }

    public Double getPaidAmount() {
        return this.paidAmount;
    }

    public RFQClaims paidAmount(Double paidAmount) {
        this.paidAmount = paidAmount;
        return this;
    }

    public void setPaidAmount(Double paidAmount) {
        this.paidAmount = paidAmount;
    }

    public Double getOutstandingAmount() {
        return this.outstandingAmount;
    }

    public RFQClaims outstandingAmount(Double outstandingAmount) {
        this.outstandingAmount = outstandingAmount;
        return this;
    }

    public void setOutstandingAmount(Double outstandingAmount) {
        this.outstandingAmount = outstandingAmount;
    }

    public RequestForQuotation getRequestForQuotation() {
        return this.requestForQuotation;
    }

    public RFQClaims requestForQuotation(RequestForQuotation requestForQuotation) {
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
        if (!(o instanceof RFQClaims)) {
            return false;
        }
        return id != null && id.equals(((RFQClaims) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RFQClaims{" +
            "id=" + getId() +
            ", rfqId=" + getRfqId() +
            ", claimYear=" + getClaimYear() +
            ", paidAmount=" + getPaidAmount() +
            ", outstandingAmount=" + getOutstandingAmount() +
            "}";
    }
}
