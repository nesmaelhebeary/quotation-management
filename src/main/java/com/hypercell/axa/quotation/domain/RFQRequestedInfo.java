package com.hypercell.axa.quotation.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A RFQRequestedInfo.
 */
@Entity
@Table(name = "rfq_requested_info")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class RFQRequestedInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "requested_by")
    private String requestedBy;

    @Column(name = "request_date")
    private LocalDate requestDate;

    @Column(name = "request_details")
    private String requestDetails;

    @Column(name = "reply_date")
    private LocalDate replyDate;

    @Column(name = "replied_by")
    private String repliedBy;

    @Column(name = "reply_details")
    private String replyDetails;

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

    public RFQRequestedInfo id(Long id) {
        this.id = id;
        return this;
    }

    public String getRequestedBy() {
        return this.requestedBy;
    }

    public RFQRequestedInfo requestedBy(String requestedBy) {
        this.requestedBy = requestedBy;
        return this;
    }

    public void setRequestedBy(String requestedBy) {
        this.requestedBy = requestedBy;
    }

    public LocalDate getRequestDate() {
        return this.requestDate;
    }

    public RFQRequestedInfo requestDate(LocalDate requestDate) {
        this.requestDate = requestDate;
        return this;
    }

    public void setRequestDate(LocalDate requestDate) {
        this.requestDate = requestDate;
    }

    public String getRequestDetails() {
        return this.requestDetails;
    }

    public RFQRequestedInfo requestDetails(String requestDetails) {
        this.requestDetails = requestDetails;
        return this;
    }

    public void setRequestDetails(String requestDetails) {
        this.requestDetails = requestDetails;
    }

    public LocalDate getReplyDate() {
        return this.replyDate;
    }

    public RFQRequestedInfo replyDate(LocalDate replyDate) {
        this.replyDate = replyDate;
        return this;
    }

    public void setReplyDate(LocalDate replyDate) {
        this.replyDate = replyDate;
    }

    public String getRepliedBy() {
        return this.repliedBy;
    }

    public RFQRequestedInfo repliedBy(String repliedBy) {
        this.repliedBy = repliedBy;
        return this;
    }

    public void setRepliedBy(String repliedBy) {
        this.repliedBy = repliedBy;
    }

    public String getReplyDetails() {
        return this.replyDetails;
    }

    public RFQRequestedInfo replyDetails(String replyDetails) {
        this.replyDetails = replyDetails;
        return this;
    }

    public void setReplyDetails(String replyDetails) {
        this.replyDetails = replyDetails;
    }

    public RequestForQuotation getRequestForQuotation() {
        return this.requestForQuotation;
    }

    public RFQRequestedInfo requestForQuotation(RequestForQuotation requestForQuotation) {
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
        if (!(o instanceof RFQRequestedInfo)) {
            return false;
        }
        return id != null && id.equals(((RFQRequestedInfo) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RFQRequestedInfo{" +
            "id=" + getId() +
            ", requestedBy='" + getRequestedBy() + "'" +
            ", requestDate='" + getRequestDate() + "'" +
            ", requestDetails='" + getRequestDetails() + "'" +
            ", replyDate='" + getReplyDate() + "'" +
            ", repliedBy='" + getRepliedBy() + "'" +
            ", replyDetails='" + getReplyDetails() + "'" +
            "}";
    }
}
