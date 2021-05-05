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

    @Column(name = "policy_type_id")
    private Long policyTypeId;

    @Column(name = "effective_date")
    private LocalDate effectiveDate;

    @Column(name = "duration")
    private Integer duration;

    @Column(name = "number_of_days")
    private Integer numberOfDays;

    @Column(name = "additional_info")
    private String additionalInfo;

    @Column(name = "mainteneace_period")
    private Integer mainteneacePeriod;

    @Column(name = "trial_period")
    private Integer trialPeriod;

    @Column(name = "submission_date")
    private LocalDate submissionDate;

    @Column(name = "inception_date")
    private LocalDate inceptionDate;

    @Column(name = "dead_line_date")
    private LocalDate deadLineDate;

    @Column(name = "jhi_current_user")
    private String currentUser;

    @Column(name = "rejection_reason")
    private String rejectionReason;

    @OneToMany(mappedBy = "requestForQuotation")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = { "siteSurveyDetails", "qDocuments", "qProducts", "qProductsInfos", "requestForQuotation" },
        allowSetters = true
    )
    private Set<Quotation> quotations = new HashSet<>();

    @OneToMany(mappedBy = "requestForQuotation")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "requestForQuotation" }, allowSetters = true)
    private Set<RFQTransactionLog> rFQTransactionLogs = new HashSet<>();

    @OneToMany(mappedBy = "requestForQuotation")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "requestForQuotation" }, allowSetters = true)
    private Set<RFQRequestedInfo> rFQRequestedInfos = new HashSet<>();

    @OneToMany(mappedBy = "requestForQuotation")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "requestForQuotation" }, allowSetters = true)
    private Set<RFQDocuments> rFQDocuments = new HashSet<>();

    @OneToMany(mappedBy = "requestForQuotation")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = { "rFQProductsItems", "rFQProductsSections", "rFQProductsExtensions", "rFQProductsDeductibles", "requestForQuotation" },
        allowSetters = true
    )
    private Set<RFQProducts> rFQProducts = new HashSet<>();

    @OneToMany(mappedBy = "requestForQuotation")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "rFQProductsInfoValueLists", "requestForQuotation" }, allowSetters = true)
    private Set<RFQProductsInfo> rFQProductsInfos = new HashSet<>();

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

    public Long getPolicyTypeId() {
        return this.policyTypeId;
    }

    public RequestForQuotation policyTypeId(Long policyTypeId) {
        this.policyTypeId = policyTypeId;
        return this;
    }

    public void setPolicyTypeId(Long policyTypeId) {
        this.policyTypeId = policyTypeId;
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

    public Integer getNumberOfDays() {
        return this.numberOfDays;
    }

    public RequestForQuotation numberOfDays(Integer numberOfDays) {
        this.numberOfDays = numberOfDays;
        return this;
    }

    public void setNumberOfDays(Integer numberOfDays) {
        this.numberOfDays = numberOfDays;
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

    public Integer getMainteneacePeriod() {
        return this.mainteneacePeriod;
    }

    public RequestForQuotation mainteneacePeriod(Integer mainteneacePeriod) {
        this.mainteneacePeriod = mainteneacePeriod;
        return this;
    }

    public void setMainteneacePeriod(Integer mainteneacePeriod) {
        this.mainteneacePeriod = mainteneacePeriod;
    }

    public Integer getTrialPeriod() {
        return this.trialPeriod;
    }

    public RequestForQuotation trialPeriod(Integer trialPeriod) {
        this.trialPeriod = trialPeriod;
        return this;
    }

    public void setTrialPeriod(Integer trialPeriod) {
        this.trialPeriod = trialPeriod;
    }

    public LocalDate getSubmissionDate() {
        return this.submissionDate;
    }

    public RequestForQuotation submissionDate(LocalDate submissionDate) {
        this.submissionDate = submissionDate;
        return this;
    }

    public void setSubmissionDate(LocalDate submissionDate) {
        this.submissionDate = submissionDate;
    }

    public LocalDate getInceptionDate() {
        return this.inceptionDate;
    }

    public RequestForQuotation inceptionDate(LocalDate inceptionDate) {
        this.inceptionDate = inceptionDate;
        return this;
    }

    public void setInceptionDate(LocalDate inceptionDate) {
        this.inceptionDate = inceptionDate;
    }

    public LocalDate getDeadLineDate() {
        return this.deadLineDate;
    }

    public RequestForQuotation deadLineDate(LocalDate deadLineDate) {
        this.deadLineDate = deadLineDate;
        return this;
    }

    public void setDeadLineDate(LocalDate deadLineDate) {
        this.deadLineDate = deadLineDate;
    }

    public String getCurrentUser() {
        return this.currentUser;
    }

    public RequestForQuotation currentUser(String currentUser) {
        this.currentUser = currentUser;
        return this;
    }

    public void setCurrentUser(String currentUser) {
        this.currentUser = currentUser;
    }

    public String getRejectionReason() {
        return this.rejectionReason;
    }

    public RequestForQuotation rejectionReason(String rejectionReason) {
        this.rejectionReason = rejectionReason;
        return this;
    }

    public void setRejectionReason(String rejectionReason) {
        this.rejectionReason = rejectionReason;
    }

    public Set<Quotation> getQuotations() {
        return this.quotations;
    }

    public RequestForQuotation quotations(Set<Quotation> quotations) {
        this.setQuotations(quotations);
        return this;
    }

    public RequestForQuotation addQuotation(Quotation quotation) {
        this.quotations.add(quotation);
        quotation.setRequestForQuotation(this);
        return this;
    }

    public RequestForQuotation removeQuotation(Quotation quotation) {
        this.quotations.remove(quotation);
        quotation.setRequestForQuotation(null);
        return this;
    }

    public void setQuotations(Set<Quotation> quotations) {
        if (this.quotations != null) {
            this.quotations.forEach(i -> i.setRequestForQuotation(null));
        }
        if (quotations != null) {
            quotations.forEach(i -> i.setRequestForQuotation(this));
        }
        this.quotations = quotations;
    }

    public Set<RFQTransactionLog> getRFQTransactionLogs() {
        return this.rFQTransactionLogs;
    }

    public RequestForQuotation rFQTransactionLogs(Set<RFQTransactionLog> rFQTransactionLogs) {
        this.setRFQTransactionLogs(rFQTransactionLogs);
        return this;
    }

    public RequestForQuotation addRFQTransactionLog(RFQTransactionLog rFQTransactionLog) {
        this.rFQTransactionLogs.add(rFQTransactionLog);
        rFQTransactionLog.setRequestForQuotation(this);
        return this;
    }

    public RequestForQuotation removeRFQTransactionLog(RFQTransactionLog rFQTransactionLog) {
        this.rFQTransactionLogs.remove(rFQTransactionLog);
        rFQTransactionLog.setRequestForQuotation(null);
        return this;
    }

    public void setRFQTransactionLogs(Set<RFQTransactionLog> rFQTransactionLogs) {
        if (this.rFQTransactionLogs != null) {
            this.rFQTransactionLogs.forEach(i -> i.setRequestForQuotation(null));
        }
        if (rFQTransactionLogs != null) {
            rFQTransactionLogs.forEach(i -> i.setRequestForQuotation(this));
        }
        this.rFQTransactionLogs = rFQTransactionLogs;
    }

    public Set<RFQRequestedInfo> getRFQRequestedInfos() {
        return this.rFQRequestedInfos;
    }

    public RequestForQuotation rFQRequestedInfos(Set<RFQRequestedInfo> rFQRequestedInfos) {
        this.setRFQRequestedInfos(rFQRequestedInfos);
        return this;
    }

    public RequestForQuotation addRFQRequestedInfo(RFQRequestedInfo rFQRequestedInfo) {
        this.rFQRequestedInfos.add(rFQRequestedInfo);
        rFQRequestedInfo.setRequestForQuotation(this);
        return this;
    }

    public RequestForQuotation removeRFQRequestedInfo(RFQRequestedInfo rFQRequestedInfo) {
        this.rFQRequestedInfos.remove(rFQRequestedInfo);
        rFQRequestedInfo.setRequestForQuotation(null);
        return this;
    }

    public void setRFQRequestedInfos(Set<RFQRequestedInfo> rFQRequestedInfos) {
        if (this.rFQRequestedInfos != null) {
            this.rFQRequestedInfos.forEach(i -> i.setRequestForQuotation(null));
        }
        if (rFQRequestedInfos != null) {
            rFQRequestedInfos.forEach(i -> i.setRequestForQuotation(this));
        }
        this.rFQRequestedInfos = rFQRequestedInfos;
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

    public Set<RFQProductsInfo> getRFQProductsInfos() {
        return this.rFQProductsInfos;
    }

    public RequestForQuotation rFQProductsInfos(Set<RFQProductsInfo> rFQProductsInfos) {
        this.setRFQProductsInfos(rFQProductsInfos);
        return this;
    }

    public RequestForQuotation addRFQProductsInfo(RFQProductsInfo rFQProductsInfo) {
        this.rFQProductsInfos.add(rFQProductsInfo);
        rFQProductsInfo.setRequestForQuotation(this);
        return this;
    }

    public RequestForQuotation removeRFQProductsInfo(RFQProductsInfo rFQProductsInfo) {
        this.rFQProductsInfos.remove(rFQProductsInfo);
        rFQProductsInfo.setRequestForQuotation(null);
        return this;
    }

    public void setRFQProductsInfos(Set<RFQProductsInfo> rFQProductsInfos) {
        if (this.rFQProductsInfos != null) {
            this.rFQProductsInfos.forEach(i -> i.setRequestForQuotation(null));
        }
        if (rFQProductsInfos != null) {
            rFQProductsInfos.forEach(i -> i.setRequestForQuotation(this));
        }
        this.rFQProductsInfos = rFQProductsInfos;
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
            ", policyTypeId=" + getPolicyTypeId() +
            ", effectiveDate='" + getEffectiveDate() + "'" +
            ", duration=" + getDuration() +
            ", numberOfDays=" + getNumberOfDays() +
            ", additionalInfo='" + getAdditionalInfo() + "'" +
            ", mainteneacePeriod=" + getMainteneacePeriod() +
            ", trialPeriod=" + getTrialPeriod() +
            ", submissionDate='" + getSubmissionDate() + "'" +
            ", inceptionDate='" + getInceptionDate() + "'" +
            ", deadLineDate='" + getDeadLineDate() + "'" +
            ", currentUser='" + getCurrentUser() + "'" +
            ", rejectionReason='" + getRejectionReason() + "'" +
            "}";
    }
}
