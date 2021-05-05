package com.hypercell.axa.quotation.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A RFQProductsItems.
 */
@Entity
@Table(name = "rfq_products_items")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class RFQProductsItems implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "product_id")
    private Long productId;

    @Column(name = "rfq_id")
    private Long rfqId;

    @Column(name = "item_name")
    private String itemName;

    @Column(name = "sum_insured")
    private Double sumInsured;

    @Column(name = "currency")
    private String currency;

    @OneToMany(mappedBy = "rFQProductsItems")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "rFQProductsItems" }, allowSetters = true)
    private Set<RFQProductsItemsDetails> rFQProductsItemsDetails = new HashSet<>();

    @OneToMany(mappedBy = "rFQProductsItems")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "rFQProductsItems" }, allowSetters = true)
    private Set<RFQProductsItemsSections> rFQProductsItemsSections = new HashSet<>();

    @OneToMany(mappedBy = "rFQProductsItems")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "rFQProductsItems" }, allowSetters = true)
    private Set<RFQProductsItemsValues> rFQProductsItemsValues = new HashSet<>();

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

    public RFQProductsItems id(Long id) {
        this.id = id;
        return this;
    }

    public Long getProductId() {
        return this.productId;
    }

    public RFQProductsItems productId(Long productId) {
        this.productId = productId;
        return this;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getRfqId() {
        return this.rfqId;
    }

    public RFQProductsItems rfqId(Long rfqId) {
        this.rfqId = rfqId;
        return this;
    }

    public void setRfqId(Long rfqId) {
        this.rfqId = rfqId;
    }

    public String getItemName() {
        return this.itemName;
    }

    public RFQProductsItems itemName(String itemName) {
        this.itemName = itemName;
        return this;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public Double getSumInsured() {
        return this.sumInsured;
    }

    public RFQProductsItems sumInsured(Double sumInsured) {
        this.sumInsured = sumInsured;
        return this;
    }

    public void setSumInsured(Double sumInsured) {
        this.sumInsured = sumInsured;
    }

    public String getCurrency() {
        return this.currency;
    }

    public RFQProductsItems currency(String currency) {
        this.currency = currency;
        return this;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Set<RFQProductsItemsDetails> getRFQProductsItemsDetails() {
        return this.rFQProductsItemsDetails;
    }

    public RFQProductsItems rFQProductsItemsDetails(Set<RFQProductsItemsDetails> rFQProductsItemsDetails) {
        this.setRFQProductsItemsDetails(rFQProductsItemsDetails);
        return this;
    }

    public RFQProductsItems addRFQProductsItemsDetails(RFQProductsItemsDetails rFQProductsItemsDetails) {
        this.rFQProductsItemsDetails.add(rFQProductsItemsDetails);
        rFQProductsItemsDetails.setRFQProductsItems(this);
        return this;
    }

    public RFQProductsItems removeRFQProductsItemsDetails(RFQProductsItemsDetails rFQProductsItemsDetails) {
        this.rFQProductsItemsDetails.remove(rFQProductsItemsDetails);
        rFQProductsItemsDetails.setRFQProductsItems(null);
        return this;
    }

    public void setRFQProductsItemsDetails(Set<RFQProductsItemsDetails> rFQProductsItemsDetails) {
        if (this.rFQProductsItemsDetails != null) {
            this.rFQProductsItemsDetails.forEach(i -> i.setRFQProductsItems(null));
        }
        if (rFQProductsItemsDetails != null) {
            rFQProductsItemsDetails.forEach(i -> i.setRFQProductsItems(this));
        }
        this.rFQProductsItemsDetails = rFQProductsItemsDetails;
    }

    public Set<RFQProductsItemsSections> getRFQProductsItemsSections() {
        return this.rFQProductsItemsSections;
    }

    public RFQProductsItems rFQProductsItemsSections(Set<RFQProductsItemsSections> rFQProductsItemsSections) {
        this.setRFQProductsItemsSections(rFQProductsItemsSections);
        return this;
    }

    public RFQProductsItems addRFQProductsItemsSections(RFQProductsItemsSections rFQProductsItemsSections) {
        this.rFQProductsItemsSections.add(rFQProductsItemsSections);
        rFQProductsItemsSections.setRFQProductsItems(this);
        return this;
    }

    public RFQProductsItems removeRFQProductsItemsSections(RFQProductsItemsSections rFQProductsItemsSections) {
        this.rFQProductsItemsSections.remove(rFQProductsItemsSections);
        rFQProductsItemsSections.setRFQProductsItems(null);
        return this;
    }

    public void setRFQProductsItemsSections(Set<RFQProductsItemsSections> rFQProductsItemsSections) {
        if (this.rFQProductsItemsSections != null) {
            this.rFQProductsItemsSections.forEach(i -> i.setRFQProductsItems(null));
        }
        if (rFQProductsItemsSections != null) {
            rFQProductsItemsSections.forEach(i -> i.setRFQProductsItems(this));
        }
        this.rFQProductsItemsSections = rFQProductsItemsSections;
    }

    public Set<RFQProductsItemsValues> getRFQProductsItemsValues() {
        return this.rFQProductsItemsValues;
    }

    public RFQProductsItems rFQProductsItemsValues(Set<RFQProductsItemsValues> rFQProductsItemsValues) {
        this.setRFQProductsItemsValues(rFQProductsItemsValues);
        return this;
    }

    public RFQProductsItems addRFQProductsItemsValues(RFQProductsItemsValues rFQProductsItemsValues) {
        this.rFQProductsItemsValues.add(rFQProductsItemsValues);
        rFQProductsItemsValues.setRFQProductsItems(this);
        return this;
    }

    public RFQProductsItems removeRFQProductsItemsValues(RFQProductsItemsValues rFQProductsItemsValues) {
        this.rFQProductsItemsValues.remove(rFQProductsItemsValues);
        rFQProductsItemsValues.setRFQProductsItems(null);
        return this;
    }

    public void setRFQProductsItemsValues(Set<RFQProductsItemsValues> rFQProductsItemsValues) {
        if (this.rFQProductsItemsValues != null) {
            this.rFQProductsItemsValues.forEach(i -> i.setRFQProductsItems(null));
        }
        if (rFQProductsItemsValues != null) {
            rFQProductsItemsValues.forEach(i -> i.setRFQProductsItems(this));
        }
        this.rFQProductsItemsValues = rFQProductsItemsValues;
    }

    public RFQProducts getRFQProducts() {
        return this.rFQProducts;
    }

    public RFQProductsItems rFQProducts(RFQProducts rFQProducts) {
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
        if (!(o instanceof RFQProductsItems)) {
            return false;
        }
        return id != null && id.equals(((RFQProductsItems) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RFQProductsItems{" +
            "id=" + getId() +
            ", productId=" + getProductId() +
            ", rfqId=" + getRfqId() +
            ", itemName='" + getItemName() + "'" +
            ", sumInsured=" + getSumInsured() +
            ", currency='" + getCurrency() + "'" +
            "}";
    }
}
