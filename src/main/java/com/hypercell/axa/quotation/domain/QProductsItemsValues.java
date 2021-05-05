package com.hypercell.axa.quotation.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A QProductsItemsValues.
 */
@Entity
@Table(name = "q_products_items_values")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class QProductsItemsValues implements Serializable {

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
    @JsonIgnoreProperties(value = { "qProductsItemsSections", "qProductsItemsValues", "qProducts" }, allowSetters = true)
    private QProductsItems qProductsItems;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public QProductsItemsValues id(Long id) {
        this.id = id;
        return this;
    }

    public Long getProductItemId() {
        return this.productItemId;
    }

    public QProductsItemsValues productItemId(Long productItemId) {
        this.productItemId = productItemId;
        return this;
    }

    public void setProductItemId(Long productItemId) {
        this.productItemId = productItemId;
    }

    public String getAttributeName() {
        return this.attributeName;
    }

    public QProductsItemsValues attributeName(String attributeName) {
        this.attributeName = attributeName;
        return this;
    }

    public void setAttributeName(String attributeName) {
        this.attributeName = attributeName;
    }

    public String getAttributeValue() {
        return this.attributeValue;
    }

    public QProductsItemsValues attributeValue(String attributeValue) {
        this.attributeValue = attributeValue;
        return this;
    }

    public void setAttributeValue(String attributeValue) {
        this.attributeValue = attributeValue;
    }

    public QProductsItems getQProductsItems() {
        return this.qProductsItems;
    }

    public QProductsItemsValues qProductsItems(QProductsItems qProductsItems) {
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
        if (!(o instanceof QProductsItemsValues)) {
            return false;
        }
        return id != null && id.equals(((QProductsItemsValues) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "QProductsItemsValues{" +
            "id=" + getId() +
            ", productItemId=" + getProductItemId() +
            ", attributeName='" + getAttributeName() + "'" +
            ", attributeValue='" + getAttributeValue() + "'" +
            "}";
    }
}
