package com.hypercell.axa.quotation.domain;

import com.hypercell.axa.quotation.domain.enumeration.DistributionType;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A QuotationDistribution.
 */
@Entity
@Table(name = "quotation_distribution")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class QuotationDistribution implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "quotation_id")
    private Long quotationId;

    @Enumerated(EnumType.STRING)
    @Column(name = "distribution_type")
    private DistributionType distributionType;

    @Column(name = "value")
    private Double value;

    @Column(name = "currency")
    private String currency;

    @Column(name = "percentage")
    private Double percentage;

    @Column(name = "rate")
    private Double rate;

    @Column(name = "premiums")
    private Double premiums;

    @Column(name = "commission_percentage")
    private Double commissionPercentage;

    @Column(name = "tax_percentage")
    private Double taxPercentage;

    @Column(name = "net_premiums")
    private Double netPremiums;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public QuotationDistribution id(Long id) {
        this.id = id;
        return this;
    }

    public Long getQuotationId() {
        return this.quotationId;
    }

    public QuotationDistribution quotationId(Long quotationId) {
        this.quotationId = quotationId;
        return this;
    }

    public void setQuotationId(Long quotationId) {
        this.quotationId = quotationId;
    }

    public DistributionType getDistributionType() {
        return this.distributionType;
    }

    public QuotationDistribution distributionType(DistributionType distributionType) {
        this.distributionType = distributionType;
        return this;
    }

    public void setDistributionType(DistributionType distributionType) {
        this.distributionType = distributionType;
    }

    public Double getValue() {
        return this.value;
    }

    public QuotationDistribution value(Double value) {
        this.value = value;
        return this;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public String getCurrency() {
        return this.currency;
    }

    public QuotationDistribution currency(String currency) {
        this.currency = currency;
        return this;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Double getPercentage() {
        return this.percentage;
    }

    public QuotationDistribution percentage(Double percentage) {
        this.percentage = percentage;
        return this;
    }

    public void setPercentage(Double percentage) {
        this.percentage = percentage;
    }

    public Double getRate() {
        return this.rate;
    }

    public QuotationDistribution rate(Double rate) {
        this.rate = rate;
        return this;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public Double getPremiums() {
        return this.premiums;
    }

    public QuotationDistribution premiums(Double premiums) {
        this.premiums = premiums;
        return this;
    }

    public void setPremiums(Double premiums) {
        this.premiums = premiums;
    }

    public Double getCommissionPercentage() {
        return this.commissionPercentage;
    }

    public QuotationDistribution commissionPercentage(Double commissionPercentage) {
        this.commissionPercentage = commissionPercentage;
        return this;
    }

    public void setCommissionPercentage(Double commissionPercentage) {
        this.commissionPercentage = commissionPercentage;
    }

    public Double getTaxPercentage() {
        return this.taxPercentage;
    }

    public QuotationDistribution taxPercentage(Double taxPercentage) {
        this.taxPercentage = taxPercentage;
        return this;
    }

    public void setTaxPercentage(Double taxPercentage) {
        this.taxPercentage = taxPercentage;
    }

    public Double getNetPremiums() {
        return this.netPremiums;
    }

    public QuotationDistribution netPremiums(Double netPremiums) {
        this.netPremiums = netPremiums;
        return this;
    }

    public void setNetPremiums(Double netPremiums) {
        this.netPremiums = netPremiums;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof QuotationDistribution)) {
            return false;
        }
        return id != null && id.equals(((QuotationDistribution) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "QuotationDistribution{" +
            "id=" + getId() +
            ", quotationId=" + getQuotationId() +
            ", distributionType='" + getDistributionType() + "'" +
            ", value=" + getValue() +
            ", currency='" + getCurrency() + "'" +
            ", percentage=" + getPercentage() +
            ", rate=" + getRate() +
            ", premiums=" + getPremiums() +
            ", commissionPercentage=" + getCommissionPercentage() +
            ", taxPercentage=" + getTaxPercentage() +
            ", netPremiums=" + getNetPremiums() +
            "}";
    }
}
