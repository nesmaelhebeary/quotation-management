package com.hypercell.axa.quotation.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.hypercell.axa.quotation.domain.enumeration.RFQStatus;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A RequestForQuotation.
 */
@Entity
@Table(name = "request_for_quotation")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class RequestForQuotation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "number")
    private String number;

    @Column(name = "broker_id")
    private Long brokerId;

    @Column(name = "customer_id")
    private Long customerId;

    @Column(name = "created_date")
    private LocalDate createdDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private RFQStatus status;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "insured_names")
    private String insuredNames;

    @Column(name = "additional_insured")
    private String additionalInsured;

    @Column(name = "benificiary_name")
    private String benificiaryName;

    @Column(name = "policy_type")
    private String policyType;

    @Column(name = "effective_date")
    private LocalDate effectiveDate;

    @Column(name = "duration")
    private Integer duration;

    @Column(name = "number_of_month")
    private Integer numberOfMonth;

    @Column(name = "additional_info")
    private String additionalInfo;

    @OneToMany(mappedBy = "requestForQuotation")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "requestForQuotation" }, allowSetters = true)
    private Set<RFQClaims> rFQClaims = new HashSet<>();

    @OneToMany(mappedBy = "requestForQuotation")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "requestForQuotation" }, allowSetters = true)
    private Set<RFQDocuments> rFQDocuments = new HashSet<>();

    @OneToMany(mappedBy = "requestForQuotation")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "rFQProductsAttrs", "rFQProductsSections", "requestForQuotation" }, allowSetters = true)
    private Set<RFQProducts> rFQProducts = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public RequestForQuotation id(Long id) {
        this.id = id;
        return this;
    }

    public String getNumber() {
        return this.number;
    }

    public RequestForQuotation number(String number) {
        this.number = number;
        return this;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Long getBrokerId() {
        return this.brokerId;
    }

    public RequestForQuotation brokerId(Long brokerId) {
        this.brokerId = brokerId;
        return this;
    }

    public void setBrokerId(Long brokerId) {
        this.brokerId = brokerId;
    }

    public Long getCustomerId() {
        return this.customerId;
    }

    public RequestForQuotation customerId(Long customerId) {
        this.customerId = customerId;
        return this;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public LocalDate getCreatedDate() {
        return this.createdDate;
    }

    public RequestForQuotation createdDate(LocalDate createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public RFQStatus getStatus() {
        return this.status;
    }

    public RequestForQuotation status(RFQStatus status) {
        this.status = status;
        return this;
    }

    public void setStatus(RFQStatus status) {
        this.status = status;
    }

    public String getCreatedBy() {
        return this.createdBy;
    }

    public RequestForQuotation createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getInsuredNames() {
        return this.insuredNames;
    }

    public RequestForQuotation insuredNames(String insuredNames) {
        this.insuredNames = insuredNames;
        return this;
    }

    public void setInsuredNames(String insuredNames) {
        this.insuredNames = insuredNames;
    }

    public String getAdditionalInsured() {
        return this.additionalInsured;
    }

    public RequestForQuotation additionalInsured(String additionalInsured) {
        this.additionalInsured = additionalInsured;
        return this;
    }

    public void setAdditionalInsured(String additionalInsured) {
        this.additionalInsured = additionalInsured;
    }

    public String getBenificiaryName() {
        return this.benificiaryName;
    }

    public RequestForQuotation benificiaryName(String benificiaryName) {
        this.benificiaryName = benificiaryName;
        return this;
    }

    public void setBenificiaryName(String benificiaryName) {
        this.benificiaryName = benificiaryName;
    }

    public String getPolicyType() {
        return this.policyType;
    }

    public RequestForQuotation policyType(String policyType) {
        this.policyType = policyType;
        return this;
    }

    public void setPolicyType(String policyType) {
        this.policyType = policyType;
    }

    public LocalDate getEffectiveDate() {
        return this.effectiveDate;
    }

    public RequestForQuotation effectiveDate(LocalDate effectiveDate) {
        this.effectiveDate = effectiveDate;
        return this;
    }

    public void setEffectiveDate(LocalDate effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    public Integer getDuration() {
        return this.duration;
    }

    public RequestForQuotation duration(Integer duration) {
        this.duration = duration;
        return this;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Integer getNumberOfMonth() {
        return this.numberOfMonth;
    }

    public RequestForQuotation numberOfMonth(Integer numberOfMonth) {
        this.numberOfMonth = numberOfMonth;
        return this;
    }

    public void setNumberOfMonth(Integer numberOfMonth) {
        this.numberOfMonth = numberOfMonth;
    }

    public String getAdditionalInfo() {
        return this.additionalInfo;
    }

    public RequestForQuotation additionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
        return this;
    }

    public void setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
    }

    public Set<RFQClaims> getRFQClaims() {
        return this.rFQClaims;
    }

    public RequestForQuotation rFQClaims(Set<RFQClaims> rFQClaims) {
        this.setRFQClaims(rFQClaims);
        return this;
    }

    public RequestForQuotation addRFQClaims(RFQClaims rFQClaims) {
        this.rFQClaims.add(rFQClaims);
        rFQClaims.setRequestForQuotation(this);
        return this;
    }

    public RequestForQuotation removeRFQClaims(RFQClaims rFQClaims) {
        this.rFQClaims.remove(rFQClaims);
        rFQClaims.setRequestForQuotation(null);
        return this;
    }

    public void setRFQClaims(Set<RFQClaims> rFQClaims) {
        if (this.rFQClaims != null) {
            this.rFQClaims.forEach(i -> i.setRequestForQuotation(null));
        }
        if (rFQClaims != null) {
            rFQClaims.forEach(i -> i.setRequestForQuotation(this));
        }
        this.rFQClaims = rFQClaims;
    }

    public Set<RFQDocuments> getRFQDocuments() {
        return this.rFQDocuments;
    }

    public RequestForQuotation rFQDocuments(Set<RFQDocuments> rFQDocuments) {
        this.setRFQDocuments(rFQDocuments);
        return this;
    }

    public RequestForQuotation addRFQDocuments(RFQDocuments rFQDocuments) {
        this.rFQDocuments.add(rFQDocuments);
        rFQDocuments.setRequestForQuotation(this);
        return this;
    }

    public RequestForQuotation removeRFQDocuments(RFQDocuments rFQDocuments) {
        this.rFQDocuments.remove(rFQDocuments);
        rFQDocuments.setRequestForQuotation(null);
        return this;
    }

    public void setRFQDocuments(Set<RFQDocuments> rFQDocuments) {
        if (this.rFQDocuments != null) {
            this.rFQDocuments.forEach(i -> i.setRequestForQuotation(null));
        }
        if (rFQDocuments != null) {
            rFQDocuments.forEach(i -> i.setRequestForQuotation(this));
        }
        this.rFQDocuments = rFQDocuments;
    }

    public Set<RFQProducts> getRFQProducts() {
        return this.rFQProducts;
    }

    public RequestForQuotation rFQProducts(Set<RFQProducts> rFQProducts) {
        this.setRFQProducts(rFQProducts);
        return this;
    }

    public RequestForQuotation addRFQProducts(RFQProducts rFQProducts) {
        this.rFQProducts.add(rFQProducts);
        rFQProducts.setRequestForQuotation(this);
        return this;
    }

    public RequestForQuotation removeRFQProducts(RFQProducts rFQProducts) {
        this.rFQProducts.remove(rFQProducts);
        rFQProducts.setRequestForQuotation(null);
        return this;
    }

    public void setRFQProducts(Set<RFQProducts> rFQProducts) {
        if (this.rFQProducts != null) {
            this.rFQProducts.forEach(i -> i.setRequestForQuotation(null));
        }
        if (rFQProducts != null) {
            rFQProducts.forEach(i -> i.setRequestForQuotation(this));
        }
        this.rFQProducts = rFQProducts;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RequestForQuotation)) {
            return false;
        }
        return id != null && id.equals(((RequestForQuotation) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RequestForQuotation{" +
            "id=" + getId() +
            ", number='" + getNumber() + "'" +
            ", brokerId=" + getBrokerId() +
            ", customerId=" + getCustomerId() +
            ", createdDate='" + getCreatedDate() + "'" +
            ", status='" + getStatus() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", insuredNames='" + getInsuredNames() + "'" +
            ", additionalInsured='" + getAdditionalInsured() + "'" +
            ", benificiaryName='" + getBenificiaryName() + "'" +
            ", policyType='" + getPolicyType() + "'" +
            ", effectiveDate='" + getEffectiveDate() + "'" +
            ", duration=" + getDuration() +
            ", numberOfMonth=" + getNumberOfMonth() +
            ", additionalInfo='" + getAdditionalInfo() + "'" +
            "}";
    }
}
