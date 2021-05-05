package com.hypercell.axa.quotation.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A QProductsSections.
 */
@Entity
@Table(name = "q_products_sections")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class QProductsSections implements Serializable {

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

    @Column(name = "quotation_id")
    private Long quotationId;

    @ManyToOne
    @JsonIgnoreProperties(value = { "qProductsItems", "qProductsSections", "quotation" }, allowSetters = true)
    private QProducts qProducts;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public QProductsSections id(Long id) {
        this.id = id;
        return this;
    }

    public String getSectionName() {
        return this.sectionName;
    }

    public QProductsSections sectionName(String sectionName) {
        this.sectionName = sectionName;
        return this;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }

    public Double getSumInsured() {
        return this.sumInsured;
    }

    public QProductsSections sumInsured(Double sumInsured) {
        this.sumInsured = sumInsured;
        return this;
    }

    public void setSumInsured(Double sumInsured) {
        this.sumInsured = sumInsured;
    }

    public String getCurrency() {
        return this.currency;
    }

    public QProductsSections currency(String currency) {
        this.currency = currency;
        return this;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Long getQuotationId() {
        return this.quotationId;
    }

    public QProductsSections quotationId(Long quotationId) {
        this.quotationId = quotationId;
        return this;
    }

    public void setQuotationId(Long quotationId) {
        this.quotationId = quotationId;
    }

    public QProducts getQProducts() {
        return this.qProducts;
    }

    public QProductsSections qProducts(QProducts qProducts) {
        this.setQProducts(qProducts);
        return this;
    }

    public void setQProducts(QProducts qProducts) {
        this.qProducts = qProducts;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof QProductsSections)) {
            return false;
        }
        return id != null && id.equals(((QProductsSections) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "QProductsSections{" +
            "id=" + getId() +
            ", sectionName='" + getSectionName() + "'" +
            ", sumInsured=" + getSumInsured() +
            ", currency='" + getCurrency() + "'" +
            ", quotationId=" + getQuotationId() +
            "}";
    }
}
