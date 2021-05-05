package com.hypercell.axa.quotation.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A QProductsItemsSections.
 */
@Entity
@Table(name = "q_products_items_sections")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class QProductsItemsSections implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "product_id")
    private Long productId;

    @Column(name = "quotation_id")
    private Long quotationId;

    @Column(name = "section_id")
    private Long sectionId;

    @Column(name = "sum_insured")
    private Double sumInsured;

    @Column(name = "currency")
    private String currency;

    @ManyToOne
    @JsonIgnoreProperties(value = { "qProductsItemsSections", "qProductsItemsValues", "qProducts" }, allowSetters = true)
    private QProductsItems qProductsItems;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public QProductsItemsSections id(Long id) {
        this.id = id;
        return this;
    }

    public Long getProductId() {
        return this.productId;
    }

    public QProductsItemsSections productId(Long productId) {
        this.productId = productId;
        return this;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getQuotationId() {
        return this.quotationId;
    }

    public QProductsItemsSections quotationId(Long quotationId) {
        this.quotationId = quotationId;
        return this;
    }

    public void setQuotationId(Long quotationId) {
        this.quotationId = quotationId;
    }

    public Long getSectionId() {
        return this.sectionId;
    }

    public QProductsItemsSections sectionId(Long sectionId) {
        this.sectionId = sectionId;
        return this;
    }

    public void setSectionId(Long sectionId) {
        this.sectionId = sectionId;
    }

    public Double getSumInsured() {
        return this.sumInsured;
    }

    public QProductsItemsSections sumInsured(Double sumInsured) {
        this.sumInsured = sumInsured;
        return this;
    }

    public void setSumInsured(Double sumInsured) {
        this.sumInsured = sumInsured;
    }

    public String getCurrency() {
        return this.currency;
    }

    public QProductsItemsSections currency(String currency) {
        this.currency = currency;
        return this;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public QProductsItems getQProductsItems() {
        return this.qProductsItems;
    }

    public QProductsItemsSections qProductsItems(QProductsItems qProductsItems) {
        this.setQProductsItems(qProductsItems);
        return this;
    }

    public void setQProductsItems(QProductsItems qProductsItems) {
        this.qProductsItems = qProductsItems;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof QProductsItemsSections)) {
            return false;
        }
        return id != null && id.equals(((QProductsItemsSections) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "QProductsItemsSections{" +
            "id=" + getId() +
            ", productId=" + getProductId() +
            ", quotationId=" + getQuotationId() +
            ", sectionId=" + getSectionId() +
            ", sumInsured=" + getSumInsured() +
            ", currency='" + getCurrency() + "'" +
            "}";
    }
}
