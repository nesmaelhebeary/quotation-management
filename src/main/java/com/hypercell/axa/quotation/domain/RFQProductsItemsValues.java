package com.hypercell.axa.quotation.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A RFQProductsItemsValues.
 */
@Entity
@Table(name = "rfq_products_items_values")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class RFQProductsItemsValues implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "product_item_id")
    private Long productItemId;

    @Column(name = "attribute_name")
    private String attributeName;

    @Column(name = "attribute_value")
    private String attributeValue;

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

    public RFQProductsItemsValues id(Long id) {
        this.id = id;
        return this;
    }

    public Long getProductItemId() {
        return this.productItemId;
    }

    public RFQProductsItemsValues productItemId(Long productItemId) {
        this.productItemId = productItemId;
        return this;
    }

    public void setProductItemId(Long productItemId) {
        this.productItemId = productItemId;
    }

    public String getAttributeName() {
        return this.attributeName;
    }

    public RFQProductsItemsValues attributeName(String attributeName) {
        this.attributeName = attributeName;
        return this;
    }

    public void setAttributeName(String attributeName) {
        this.attributeName = attributeName;
    }

    public String getAttributeValue() {
        return this.attributeValue;
    }

    public RFQProductsItemsValues attributeValue(String attributeValue) {
        this.attributeValue = attributeValue;
        return this;
    }

    public void setAttributeValue(String attributeValue) {
        this.attributeValue = attributeValue;
    }

    public RFQProductsItems getRFQProductsItems() {
        return this.rFQProductsItems;
    }

    public RFQProductsItemsValues rFQProductsItems(RFQProductsItems rFQProductsItems) {
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
        if (!(o instanceof RFQProductsItemsValues)) {
            return false;
        }
        return id != null && id.equals(((RFQProductsItemsValues) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RFQProductsItemsValues{" +
            "id=" + getId() +
            ", productItemId=" + getProductItemId() +
            ", attributeName='" + getAttributeName() + "'" +
            ", attributeValue='" + getAttributeValue() + "'" +
            "}";
    }
}
