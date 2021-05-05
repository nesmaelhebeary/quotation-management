package com.hypercell.axa.quotation.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A QProductsItems.
 */
@Entity
@Table(name = "q_products_items")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class QProductsItems implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "product_id")
    private Long productId;

    @Column(name = "quotation_id")
    private Long quotationId;

    @Column(name = "item_name")
    private String itemName;

    @Column(name = "item_desc")
    private String itemDesc;

    @Column(name = "sum_insured")
    private Double sumInsured;

    @Column(name = "currency")
    private String currency;

    @OneToMany(mappedBy = "qProductsItems")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "qProductsItems" }, allowSetters = true)
    private Set<QProductsItemsSections> qProductsItemsSections = new HashSet<>();

    @OneToMany(mappedBy = "qProductsItems")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "qProductsItems" }, allowSetters = true)
    private Set<QProductsItemsValues> qProductsItemsValues = new HashSet<>();

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

    public QProductsItems id(Long id) {
        this.id = id;
        return this;
    }

    public Long getProductId() {
        return this.productId;
    }

    public QProductsItems productId(Long productId) {
        this.productId = productId;
        return this;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getQuotationId() {
        return this.quotationId;
    }

    public QProductsItems quotationId(Long quotationId) {
        this.quotationId = quotationId;
        return this;
    }

    public void setQuotationId(Long quotationId) {
        this.quotationId = quotationId;
    }

    public String getItemName() {
        return this.itemName;
    }

    public QProductsItems itemName(String itemName) {
        this.itemName = itemName;
        return this;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemDesc() {
        return this.itemDesc;
    }

    public QProductsItems itemDesc(String itemDesc) {
        this.itemDesc = itemDesc;
        return this;
    }

    public void setItemDesc(String itemDesc) {
        this.itemDesc = itemDesc;
    }

    public Double getSumInsured() {
        return this.sumInsured;
    }

    public QProductsItems sumInsured(Double sumInsured) {
        this.sumInsured = sumInsured;
        return this;
    }

    public void setSumInsured(Double sumInsured) {
        this.sumInsured = sumInsured;
    }

    public String getCurrency() {
        return this.currency;
    }

    public QProductsItems currency(String currency) {
        this.currency = currency;
        return this;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Set<QProductsItemsSections> getQProductsItemsSections() {
        return this.qProductsItemsSections;
    }

    public QProductsItems qProductsItemsSections(Set<QProductsItemsSections> qProductsItemsSections) {
        this.setQProductsItemsSections(qProductsItemsSections);
        return this;
    }

    public QProductsItems addQProductsItemsSections(QProductsItemsSections qProductsItemsSections) {
        this.qProductsItemsSections.add(qProductsItemsSections);
        qProductsItemsSections.setQProductsItems(this);
        return this;
    }

    public QProductsItems removeQProductsItemsSections(QProductsItemsSections qProductsItemsSections) {
        this.qProductsItemsSections.remove(qProductsItemsSections);
        qProductsItemsSections.setQProductsItems(null);
        return this;
    }

    public void setQProductsItemsSections(Set<QProductsItemsSections> qProductsItemsSections) {
        if (this.qProductsItemsSections != null) {
            this.qProductsItemsSections.forEach(i -> i.setQProductsItems(null));
        }
        if (qProductsItemsSections != null) {
            qProductsItemsSections.forEach(i -> i.setQProductsItems(this));
        }
        this.qProductsItemsSections = qProductsItemsSections;
    }

    public Set<QProductsItemsValues> getQProductsItemsValues() {
        return this.qProductsItemsValues;
    }

    public QProductsItems qProductsItemsValues(Set<QProductsItemsValues> qProductsItemsValues) {
        this.setQProductsItemsValues(qProductsItemsValues);
        return this;
    }

    public QProductsItems addQProductsItemsValues(QProductsItemsValues qProductsItemsValues) {
        this.qProductsItemsValues.add(qProductsItemsValues);
        qProductsItemsValues.setQProductsItems(this);
        return this;
    }

    public QProductsItems removeQProductsItemsValues(QProductsItemsValues qProductsItemsValues) {
        this.qProductsItemsValues.remove(qProductsItemsValues);
        qProductsItemsValues.setQProductsItems(null);
        return this;
    }

    public void setQProductsItemsValues(Set<QProductsItemsValues> qProductsItemsValues) {
        if (this.qProductsItemsValues != null) {
            this.qProductsItemsValues.forEach(i -> i.setQProductsItems(null));
        }
        if (qProductsItemsValues != null) {
            qProductsItemsValues.forEach(i -> i.setQProductsItems(this));
        }
        this.qProductsItemsValues = qProductsItemsValues;
    }

    public QProducts getQProducts() {
        return this.qProducts;
    }

    public QProductsItems qProducts(QProducts qProducts) {
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
        if (!(o instanceof QProductsItems)) {
            return false;
        }
        return id != null && id.equals(((QProductsItems) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "QProductsItems{" +
            "id=" + getId() +
            ", productId=" + getProductId() +
            ", quotationId=" + getQuotationId() +
            ", itemName='" + getItemName() + "'" +
            ", itemDesc='" + getItemDesc() + "'" +
            ", sumInsured=" + getSumInsured() +
            ", currency='" + getCurrency() + "'" +
            "}";
    }
}
