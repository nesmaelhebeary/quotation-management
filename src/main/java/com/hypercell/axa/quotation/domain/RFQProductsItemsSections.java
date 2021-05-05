package com.hypercell.axa.quotation.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A RFQProductsItemsSections.
 */
@Entity
@Table(name = "rfq_products_items_sections")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class RFQProductsItemsSections implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "product_id")
    private Long productId;

    @Column(name = "rfq_id")
    private Long rfqId;

    @Column(name = "section_id")
    private Long sectionId;

    @Column(name = "sum_insured")
    private Double sumInsured;

    @Column(name = "currency")
    private String currency;

    @ManyToOne
    @JsonIgnoreProperties(
        value = { "rFQProductsItemsDetails", "rFQProductsItemsSections", "rFQProductsItemsValues", "rFQProducts" },
        allowSetters = true
    )
    private RFQProductsItems rFQProductsItems;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public RFQProductsItemsSections id(Long id) {
        this.id = id;
        return this;
    }

    public Long getProductId() {
        return this.productId;
    }

    public RFQProductsItemsSections productId(Long productId) {
        this.productId = productId;
        return this;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getRfqId() {
        return this.rfqId;
    }

    public RFQProductsItemsSections rfqId(Long rfqId) {
        this.rfqId = rfqId;
        return this;
    }

    public void setRfqId(Long rfqId) {
        this.rfqId = rfqId;
    }

    public Long getSectionId() {
        return this.sectionId;
    }

    public RFQProductsItemsSections sectionId(Long sectionId) {
        this.sectionId = sectionId;
        return this;
    }

    public void setSectionId(Long sectionId) {
        this.sectionId = sectionId;
    }

    public Double getSumInsured() {
        return this.sumInsured;
    }

    public RFQProductsItemsSections sumInsured(Double sumInsured) {
        this.sumInsured = sumInsured;
        return this;
    }

    public void setSumInsured(Double sumInsured) {
        this.sumInsured = sumInsured;
    }

    public String getCurrency() {
        return this.currency;
    }

    public RFQProductsItemsSections currency(String currency) {
        this.currency = currency;
        return this;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public RFQProductsItems getRFQProductsItems() {
        return this.rFQProductsItems;
    }

    public RFQProductsItemsSections rFQProductsItems(RFQProductsItems rFQProductsItems) {
        this.setRFQProductsItems(rFQProductsItems);
        return this;
    }

    public void setRFQProductsItems(RFQProductsItems rFQProductsItems) {
        this.rFQProductsItems = rFQProductsItems;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RFQProductsItemsSections)) {
            return false;
        }
        return id != null && id.equals(((RFQProductsItemsSections) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RFQProductsItemsSections{" +
            "id=" + getId() +
            ", productId=" + getProductId() +
            ", rfqId=" + getRfqId() +
            ", sectionId=" + getSectionId() +
            ", sumInsured=" + getSumInsured() +
            ", currency='" + getCurrency() + "'" +
            "}";
    }
}
