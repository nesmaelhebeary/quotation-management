package com.hypercell.axa.quotation.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.hypercell.axa.quotation.domain.enumeration.QStatus;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Quotation.
 */
@Entity
@Table(name = "quotation")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Quotation implements Serializable {

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
    private QStatus status;

    @Column(name = "version")
    private Integer version;

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

    @Column(name = "under_writer_name")
    private String underWriterName;

    @Column(name = "expiry_date")
    private LocalDate expiryDate;

    @Column(name = "total_sum_insured")
    private Double totalSumInsured;

    @Column(name = "total_mpl")
    private Double totalMPL;

    @Column(name = "rfq_id")
    private Long rfqId;

    @Column(name = "average_rate")
    private Double averageRate;

    @Column(name = "site_survey_needed")
    private Boolean siteSurveyNeeded;

    @Column(name = "has_extension")
    private Boolean hasExtension;

    @Column(name = "rate_of_exchange_usd")
    private Double rateOfExchangeUSD;

    @Column(name = "rate_of_exchange_eu")
    private Double rateOfExchangeEU;

    @Column(name = "line_settings_percentage")
    private Double lineSettingsPercentage;

    @Column(name = "nat_cat_percentage")
    private Double natCatPercentage;

    @Column(name = "axa_share")
    private Double axaShare;

    @JsonIgnoreProperties(value = { "siteSurveyDocuments", "quotation" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private SiteSurveyDetails siteSurveyDetails;

    @OneToMany(mappedBy = "quotation")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "quotation" }, allowSetters = true)
    private Set<QDocuments> qDocuments = new HashSet<>();

    @OneToMany(mappedBy = "quotation")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "qProductsItems", "qProductsSections", "quotation" }, allowSetters = true)
    private Set<QProducts> qProducts = new HashSet<>();

    @OneToMany(mappedBy = "quotation")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "qProductsInfoValueLists", "quotation" }, allowSetters = true)
    private Set<QProductsInfo> qProductsInfos = new HashSet<>();

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

    public Quotation id(Long id) {
        this.id = id;
        return this;
    }

    public String getNumber() {
        return this.number;
    }

    public Quotation number(String number) {
        this.number = number;
        return this;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Long getBrokerId() {
        return this.brokerId;
    }

    public Quotation brokerId(Long brokerId) {
        this.brokerId = brokerId;
        return this;
    }

    public void setBrokerId(Long brokerId) {
        this.brokerId = brokerId;
    }

    public Long getCustomerId() {
        return this.customerId;
    }

    public Quotation customerId(Long customerId) {
        this.customerId = customerId;
        return this;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public LocalDate getCreatedDate() {
        return this.createdDate;
    }

    public Quotation createdDate(LocalDate createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public QStatus getStatus() {
        return this.status;
    }

    public Quotation status(QStatus status) {
        this.status = status;
        return this;
    }

    public void setStatus(QStatus status) {
        this.status = status;
    }

    public Integer getVersion() {
        return this.version;
    }

    public Quotation version(Integer version) {
        this.version = version;
        return this;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getCreatedBy() {
        return this.createdBy;
    }

    public Quotation createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getInsuredNames() {
        return this.insuredNames;
    }

    public Quotation insuredNames(String insuredNames) {
        this.insuredNames = insuredNames;
        return this;
    }

    public void setInsuredNames(String insuredNames) {
        this.insuredNames = insuredNames;
    }

    public String getAdditionalInsured() {
        return this.additionalInsured;
    }

    public Quotation additionalInsured(String additionalInsured) {
        this.additionalInsured = additionalInsured;
        return this;
    }

    public void setAdditionalInsured(String additionalInsured) {
        this.additionalInsured = additionalInsured;
    }

    public String getBenificiaryName() {
        return this.benificiaryName;
    }

    public Quotation benificiaryName(String benificiaryName) {
        this.benificiaryName = benificiaryName;
        return this;
    }

    public void setBenificiaryName(String benificiaryName) {
        this.benificiaryName = benificiaryName;
    }

    public Long getPolicyTypeId() {
        return this.policyTypeId;
    }

    public Quotation policyTypeId(Long policyTypeId) {
        this.policyTypeId = policyTypeId;
        return this;
    }

    public void setPolicyTypeId(Long policyTypeId) {
        this.policyTypeId = policyTypeId;
    }

    public LocalDate getEffectiveDate() {
        return this.effectiveDate;
    }

    public Quotation effectiveDate(LocalDate effectiveDate) {
        this.effectiveDate = effectiveDate;
        return this;
    }

    public void setEffectiveDate(LocalDate effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    public Integer getDuration() {
        return this.duration;
    }

    public Quotation duration(Integer duration) {
        this.duration = duration;
        return this;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Integer getNumberOfDays() {
        return this.numberOfDays;
    }

    public Quotation numberOfDays(Integer numberOfDays) {
        this.numberOfDays = numberOfDays;
        return this;
    }

    public void setNumberOfDays(Integer numberOfDays) {
        this.numberOfDays = numberOfDays;
    }

    public String getAdditionalInfo() {
        return this.additionalInfo;
    }

    public Quotation additionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
        return this;
    }

    public void setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
    }

    public Integer getMainteneacePeriod() {
        return this.mainteneacePeriod;
    }

    public Quotation mainteneacePeriod(Integer mainteneacePeriod) {
        this.mainteneacePeriod = mainteneacePeriod;
        return this;
    }

    public void setMainteneacePeriod(Integer mainteneacePeriod) {
        this.mainteneacePeriod = mainteneacePeriod;
    }

    public Integer getTrialPeriod() {
        return this.trialPeriod;
    }

    public Quotation trialPeriod(Integer trialPeriod) {
        this.trialPeriod = trialPeriod;
        return this;
    }

    public void setTrialPeriod(Integer trialPeriod) {
        this.trialPeriod = trialPeriod;
    }

    public String getUnderWriterName() {
        return this.underWriterName;
    }

    public Quotation underWriterName(String underWriterName) {
        this.underWriterName = underWriterName;
        return this;
    }

    public void setUnderWriterName(String underWriterName) {
        this.underWriterName = underWriterName;
    }

    public LocalDate getExpiryDate() {
        return this.expiryDate;
    }

    public Quotation expiryDate(LocalDate expiryDate) {
        this.expiryDate = expiryDate;
        return this;
    }

    public void setExpiryDate(LocalDate expiryDate) {
        this.expiryDate = expiryDate;
    }

    public Double getTotalSumInsured() {
        return this.totalSumInsured;
    }

    public Quotation totalSumInsured(Double totalSumInsured) {
        this.totalSumInsured = totalSumInsured;
        return this;
    }

    public void setTotalSumInsured(Double totalSumInsured) {
        this.totalSumInsured = totalSumInsured;
    }

    public Double getTotalMPL() {
        return this.totalMPL;
    }

    public Quotation totalMPL(Double totalMPL) {
        this.totalMPL = totalMPL;
        return this;
    }

    public void setTotalMPL(Double totalMPL) {
        this.totalMPL = totalMPL;
    }

    public Long getRfqId() {
        return this.rfqId;
    }

    public Quotation rfqId(Long rfqId) {
        this.rfqId = rfqId;
        return this;
    }

    public void setRfqId(Long rfqId) {
        this.rfqId = rfqId;
    }

    public Double getAverageRate() {
        return this.averageRate;
    }

    public Quotation averageRate(Double averageRate) {
        this.averageRate = averageRate;
        return this;
    }

    public void setAverageRate(Double averageRate) {
        this.averageRate = averageRate;
    }

    public Boolean getSiteSurveyNeeded() {
        return this.siteSurveyNeeded;
    }

    public Quotation siteSurveyNeeded(Boolean siteSurveyNeeded) {
        this.siteSurveyNeeded = siteSurveyNeeded;
        return this;
    }

    public void setSiteSurveyNeeded(Boolean siteSurveyNeeded) {
        this.siteSurveyNeeded = siteSurveyNeeded;
    }

    public Boolean getHasExtension() {
        return this.hasExtension;
    }

    public Quotation hasExtension(Boolean hasExtension) {
        this.hasExtension = hasExtension;
        return this;
    }

    public void setHasExtension(Boolean hasExtension) {
        this.hasExtension = hasExtension;
    }

    public Double getRateOfExchangeUSD() {
        return this.rateOfExchangeUSD;
    }

    public Quotation rateOfExchangeUSD(Double rateOfExchangeUSD) {
        this.rateOfExchangeUSD = rateOfExchangeUSD;
        return this;
    }

    public void setRateOfExchangeUSD(Double rateOfExchangeUSD) {
        this.rateOfExchangeUSD = rateOfExchangeUSD;
    }

    public Double getRateOfExchangeEU() {
        return this.rateOfExchangeEU;
    }

    public Quotation rateOfExchangeEU(Double rateOfExchangeEU) {
        this.rateOfExchangeEU = rateOfExchangeEU;
        return this;
    }

    public void setRateOfExchangeEU(Double rateOfExchangeEU) {
        this.rateOfExchangeEU = rateOfExchangeEU;
    }

    public Double getLineSettingsPercentage() {
        return this.lineSettingsPercentage;
    }

    public Quotation lineSettingsPercentage(Double lineSettingsPercentage) {
        this.lineSettingsPercentage = lineSettingsPercentage;
        return this;
    }

    public void setLineSettingsPercentage(Double lineSettingsPercentage) {
        this.lineSettingsPercentage = lineSettingsPercentage;
    }

    public Double getNatCatPercentage() {
        return this.natCatPercentage;
    }

    public Quotation natCatPercentage(Double natCatPercentage) {
        this.natCatPercentage = natCatPercentage;
        return this;
    }

    public void setNatCatPercentage(Double natCatPercentage) {
        this.natCatPercentage = natCatPercentage;
    }

    public Double getAxaShare() {
        return this.axaShare;
    }

    public Quotation axaShare(Double axaShare) {
        this.axaShare = axaShare;
        return this;
    }

    public void setAxaShare(Double axaShare) {
        this.axaShare = axaShare;
    }

    public SiteSurveyDetails getSiteSurveyDetails() {
        return this.siteSurveyDetails;
    }

    public Quotation siteSurveyDetails(SiteSurveyDetails siteSurveyDetails) {
        this.setSiteSurveyDetails(siteSurveyDetails);
        return this;
    }

    public void setSiteSurveyDetails(SiteSurveyDetails siteSurveyDetails) {
        this.siteSurveyDetails = siteSurveyDetails;
    }

    public Set<QDocuments> getQDocuments() {
        return this.qDocuments;
    }

    public Quotation qDocuments(Set<QDocuments> qDocuments) {
        this.setQDocuments(qDocuments);
        return this;
    }

    public Quotation addQDocuments(QDocuments qDocuments) {
        this.qDocuments.add(qDocuments);
        qDocuments.setQuotation(this);
        return this;
    }

    public Quotation removeQDocuments(QDocuments qDocuments) {
        this.qDocuments.remove(qDocuments);
        qDocuments.setQuotation(null);
        return this;
    }

    public void setQDocuments(Set<QDocuments> qDocuments) {
        if (this.qDocuments != null) {
            this.qDocuments.forEach(i -> i.setQuotation(null));
        }
        if (qDocuments != null) {
            qDocuments.forEach(i -> i.setQuotation(this));
        }
        this.qDocuments = qDocuments;
    }

    public Set<QProducts> getQProducts() {
        return this.qProducts;
    }

    public Quotation qProducts(Set<QProducts> qProducts) {
        this.setQProducts(qProducts);
        return this;
    }

    public Quotation addQProducts(QProducts qProducts) {
        this.qProducts.add(qProducts);
        qProducts.setQuotation(this);
        return this;
    }

    public Quotation removeQProducts(QProducts qProducts) {
        this.qProducts.remove(qProducts);
        qProducts.setQuotation(null);
        return this;
    }

    public void setQProducts(Set<QProducts> qProducts) {
        if (this.qProducts != null) {
            this.qProducts.forEach(i -> i.setQuotation(null));
        }
        if (qProducts != null) {
            qProducts.forEach(i -> i.setQuotation(this));
        }
        this.qProducts = qProducts;
    }

    public Set<QProductsInfo> getQProductsInfos() {
        return this.qProductsInfos;
    }

    public Quotation qProductsInfos(Set<QProductsInfo> qProductsInfos) {
        this.setQProductsInfos(qProductsInfos);
        return this;
    }

    public Quotation addQProductsInfo(QProductsInfo qProductsInfo) {
        this.qProductsInfos.add(qProductsInfo);
        qProductsInfo.setQuotation(this);
        return this;
    }

    public Quotation removeQProductsInfo(QProductsInfo qProductsInfo) {
        this.qProductsInfos.remove(qProductsInfo);
        qProductsInfo.setQuotation(null);
        return this;
    }

    public void setQProductsInfos(Set<QProductsInfo> qProductsInfos) {
        if (this.qProductsInfos != null) {
            this.qProductsInfos.forEach(i -> i.setQuotation(null));
        }
        if (qProductsInfos != null) {
            qProductsInfos.forEach(i -> i.setQuotation(this));
        }
        this.qProductsInfos = qProductsInfos;
    }

    public RequestForQuotation getRequestForQuotation() {
        return this.requestForQuotation;
    }

    public Quotation requestForQuotation(RequestForQuotation requestForQuotation) {
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
        if (!(o instanceof Quotation)) {
            return false;
        }
        return id != null && id.equals(((Quotation) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Quotation{" +
            "id=" + getId() +
            ", number='" + getNumber() + "'" +
            ", brokerId=" + getBrokerId() +
            ", customerId=" + getCustomerId() +
            ", createdDate='" + getCreatedDate() + "'" +
            ", status='" + getStatus() + "'" +
            ", version=" + getVersion() +
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
            ", underWriterName='" + getUnderWriterName() + "'" +
            ", expiryDate='" + getExpiryDate() + "'" +
            ", totalSumInsured=" + getTotalSumInsured() +
            ", totalMPL=" + getTotalMPL() +
            ", rfqId=" + getRfqId() +
            ", averageRate=" + getAverageRate() +
            ", siteSurveyNeeded='" + getSiteSurveyNeeded() + "'" +
            ", hasExtension='" + getHasExtension() + "'" +
            ", rateOfExchangeUSD=" + getRateOfExchangeUSD() +
            ", rateOfExchangeEU=" + getRateOfExchangeEU() +
            ", lineSettingsPercentage=" + getLineSettingsPercentage() +
            ", natCatPercentage=" + getNatCatPercentage() +
            ", axaShare=" + getAxaShare() +
            "}";
    }
}
