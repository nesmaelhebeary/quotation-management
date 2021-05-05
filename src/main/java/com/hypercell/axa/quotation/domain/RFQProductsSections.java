package com.hypercell.axa.quotation.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A RFQProductsSections.
 */
@Entity
@Table(name = "rfq_products_sections")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class RFQProductsSections implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "section_name")
    private String sectionName;

    @Column(name = "sum_insured")
    private Double sumInsured;

    @Column(name = "currency")
    private String currency;

    @Column(name = "rfq_id")
    private Long rfqId;

    @Column(name = "section_id")
    private Long sectionId;

    @Column(name = "turn_over")
    private Double turnOver;

    @Column(name = "max_per_time")
    private Double maxPerTime;

    @ManyToOne
    @JsonIgnoreProperties(
        value = { "rFQProductsItems", "rFQProductsSections", "rFQProductsExtensions", "rFQProductsDeductibles", "requestForQuotation" },
        allowSetters = true
    )
    private RFQProducts rFQProducts;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public RFQProductsSections id(Long id) {
        this.id = id;
        return this;
    }

    public String getSectionName() {
        return this.sectionName;
    }

    public RFQProductsSections sectionName(String sectionName) {
        this.sectionName = sectionName;
        return this;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }

    public Double getSumInsured() {
        return this.sumInsured;
    }

    public RFQProductsSections sumInsured(Double sumInsured) {
        this.sumInsured = sumInsured;
        return this;
    }

    public void setSumInsured(Double sumInsured) {
        this.sumInsured = sumInsured;
    }

    public String getCurrency() {
        return this.currency;
    }

    public RFQProductsSections currency(String currency) {
        this.currency = currency;
        return this;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Long getRfqId() {
        return this.rfqId;
    }

    public RFQProductsSections rfqId(Long rfqId) {
        this.rfqId = rfqId;
        return this;
    }

    public void setRfqId(Long rfqId) {
        this.rfqId = rfqId;
    }

    public Long getSectionId() {
        return this.sectionId;
    }

    public RFQProductsSections sectionId(Long sectionId) {
        this.sectionId = sectionId;
        return this;
    }

    public void setSectionId(Long sectionId) {
        this.sectionId = sectionId;
    }

    public Double getTurnOver() {
        return this.turnOver;
    }

    public RFQProductsSections turnOver(Double turnOver) {
        this.turnOver = turnOver;
        return this;
    }

    public void setTurnOver(Double turnOver) {
        this.turnOver = turnOver;
    }

    public Double getMaxPerTime() {
        return this.maxPerTime;
    }

    public RFQProductsSections maxPerTime(Double maxPerTime) {
        this.maxPerTime = maxPerTime;
        return this;
    }

    public void setMaxPerTime(Double maxPerTime) {
        this.maxPerTime = maxPerTime;
    }

    public RFQProducts getRFQProducts() {
        return this.rFQProducts;
    }

    public RFQProductsSections rFQProducts(RFQProducts rFQProducts) {
        this.setRFQProducts(rFQProducts);
        return this;
    }

    public void setRFQProducts(RFQProducts rFQProducts) {
        this.rFQProducts = rFQProducts;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RFQProductsSections)) {
            return false;
        }
        return id != null && id.equals(((RFQProductsSections) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RFQProductsSections{" +
            "id=" + getId() +
            ", sectionName='" + getSectionName() + "'" +
            ", sumInsured=" + getSumInsured() +
            ", currency='" + getCurrency() + "'" +
            ", rfqId=" + getRfqId() +
            ", sectionId=" + getSectionId() +
            ", turnOver=" + getTurnOver() +
            ", maxPerTime=" + getMaxPerTime() +
            "}";
    }
}
