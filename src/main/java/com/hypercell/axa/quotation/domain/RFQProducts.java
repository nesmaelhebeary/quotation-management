package com.hypercell.axa.quotation.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.hypercell.axa.quotation.domain.enumeration.CoverType;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A RFQProducts.
 */
@Entity
@Table(name = "rfq_products")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class RFQProducts implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "product_id")
    private Long productId;

    @Column(name = "line_type_id")
    private Long lineTypeId;

    @Enumerated(EnumType.STRING)
    @Column(name = "cover_type")
    private CoverType coverType;

    @Column(name = "categories")
    private String categories;

    @OneToMany(mappedBy = "rFQProducts")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = { "rFQProductsItemsDetails", "rFQProductsItemsSections", "rFQProductsItemsValues", "rFQProducts" },
        allowSetters = true
    )
    private Set<RFQProductsItems> rFQProductsItems = new HashSet<>();

    @OneToMany(mappedBy = "rFQProducts")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "rFQProducts" }, allowSetters = true)
    private Set<RFQProductsSections> rFQProductsSections = new HashSet<>();

    @OneToMany(mappedBy = "rFQProducts")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "rFQProducts" }, allowSetters = true)
    private Set<RFQProductsExtensions> rFQProductsExtensions = new HashSet<>();

    @OneToMany(mappedBy = "rFQProducts")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "rFQProducts" }, allowSetters = true)
    private Set<RFQProductsDeductibles> rFQProductsDeductibles = new HashSet<>();

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

    public RFQProducts id(Long id) {
        this.id = id;
        return this;
    }

    public Long getProductId() {
        return this.productId;
    }

    public RFQProducts productId(Long productId) {
        this.productId = productId;
        return this;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getLineTypeId() {
        return this.lineTypeId;
    }

    public RFQProducts lineTypeId(Long lineTypeId) {
        this.lineTypeId = lineTypeId;
        return this;
    }

    public void setLineTypeId(Long lineTypeId) {
        this.lineTypeId = lineTypeId;
    }

    public CoverType getCoverType() {
        return this.coverType;
    }

    public RFQProducts coverType(CoverType coverType) {
        this.coverType = coverType;
        return this;
    }

    public void setCoverType(CoverType coverType) {
        this.coverType = coverType;
    }

    public String getCategories() {
        return this.categories;
    }

    public RFQProducts categories(String categories) {
        this.categories = categories;
        return this;
    }

    public void setCategories(String categories) {
        this.categories = categories;
    }

    public Set<RFQProductsItems> getRFQProductsItems() {
        return this.rFQProductsItems;
    }

    public RFQProducts rFQProductsItems(Set<RFQProductsItems> rFQProductsItems) {
        this.setRFQProductsItems(rFQProductsItems);
        return this;
    }

    public RFQProducts addRFQProductsItems(RFQProductsItems rFQProductsItems) {
        this.rFQProductsItems.add(rFQProductsItems);
        rFQProductsItems.setRFQProducts(this);
        return this;
    }

    public RFQProducts removeRFQProductsItems(RFQProductsItems rFQProductsItems) {
        this.rFQProductsItems.remove(rFQProductsItems);
        rFQProductsItems.setRFQProducts(null);
        return this;
    }

    public void setRFQProductsItems(Set<RFQProductsItems> rFQProductsItems) {
        if (this.rFQProductsItems != null) {
            this.rFQProductsItems.forEach(i -> i.setRFQProducts(null));
        }
        if (rFQProductsItems != null) {
            rFQProductsItems.forEach(i -> i.setRFQProducts(this));
        }
        this.rFQProductsItems = rFQProductsItems;
    }

    public Set<RFQProductsSections> getRFQProductsSections() {
        return this.rFQProductsSections;
    }

    public RFQProducts rFQProductsSections(Set<RFQProductsSections> rFQProductsSections) {
        this.setRFQProductsSections(rFQProductsSections);
        return this;
    }

    public RFQProducts addRFQProductsSections(RFQProductsSections rFQProductsSections) {
        this.rFQProductsSections.add(rFQProductsSections);
        rFQProductsSections.setRFQProducts(this);
        return this;
    }

    public RFQProducts removeRFQProductsSections(RFQProductsSections rFQProductsSections) {
        this.rFQProductsSections.remove(rFQProductsSections);
        rFQProductsSections.setRFQProducts(null);
        return this;
    }

    public void setRFQProductsSections(Set<RFQProductsSections> rFQProductsSections) {
        if (this.rFQProductsSections != null) {
            this.rFQProductsSections.forEach(i -> i.setRFQProducts(null));
        }
        if (rFQProductsSections != null) {
            rFQProductsSections.forEach(i -> i.setRFQProducts(this));
        }
        this.rFQProductsSections = rFQProductsSections;
    }

    public Set<RFQProductsExtensions> getRFQProductsExtensions() {
        return this.rFQProductsExtensions;
    }

    public RFQProducts rFQProductsExtensions(Set<RFQProductsExtensions> rFQProductsExtensions) {
        this.setRFQProductsExtensions(rFQProductsExtensions);
        return this;
    }

    public RFQProducts addRFQProductsExtensions(RFQProductsExtensions rFQProductsExtensions) {
        this.rFQProductsExtensions.add(rFQProductsExtensions);
        rFQProductsExtensions.setRFQProducts(this);
        return this;
    }

    public RFQProducts removeRFQProductsExtensions(RFQProductsExtensions rFQProductsExtensions) {
        this.rFQProductsExtensions.remove(rFQProductsExtensions);
        rFQProductsExtensions.setRFQProducts(null);
        return this;
    }

    public void setRFQProductsExtensions(Set<RFQProductsExtensions> rFQProductsExtensions) {
        if (this.rFQProductsExtensions != null) {
            this.rFQProductsExtensions.forEach(i -> i.setRFQProducts(null));
        }
        if (rFQProductsExtensions != null) {
            rFQProductsExtensions.forEach(i -> i.setRFQProducts(this));
        }
        this.rFQProductsExtensions = rFQProductsExtensions;
    }

    public Set<RFQProductsDeductibles> getRFQProductsDeductibles() {
        return this.rFQProductsDeductibles;
    }

    public RFQProducts rFQProductsDeductibles(Set<RFQProductsDeductibles> rFQProductsDeductibles) {
        this.setRFQProductsDeductibles(rFQProductsDeductibles);
        return this;
    }

    public RFQProducts addRFQProductsDeductibles(RFQProductsDeductibles rFQProductsDeductibles) {
        this.rFQProductsDeductibles.add(rFQProductsDeductibles);
        rFQProductsDeductibles.setRFQProducts(this);
        return this;
    }

    public RFQProducts removeRFQProductsDeductibles(RFQProductsDeductibles rFQProductsDeductibles) {
        this.rFQProductsDeductibles.remove(rFQProductsDeductibles);
        rFQProductsDeductibles.setRFQProducts(null);
        return this;
    }

    public void setRFQProductsDeductibles(Set<RFQProductsDeductibles> rFQProductsDeductibles) {
        if (this.rFQProductsDeductibles != null) {
            this.rFQProductsDeductibles.forEach(i -> i.setRFQProducts(null));
        }
        if (rFQProductsDeductibles != null) {
            rFQProductsDeductibles.forEach(i -> i.setRFQProducts(this));
        }
        this.rFQProductsDeductibles = rFQProductsDeductibles;
    }

    public RequestForQuotation getRequestForQuotation() {
        return this.requestForQuotation;
    }

    public RFQProducts requestForQuotation(RequestForQuotation requestForQuotation) {
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
        if (!(o instanceof RFQProducts)) {
            return false;
        }
        return id != null && id.equals(((RFQProducts) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RFQProducts{" +
            "id=" + getId() +
            ", productId=" + getProductId() +
            ", lineTypeId=" + getLineTypeId() +
            ", coverType='" + getCoverType() + "'" +
            ", categories='" + getCategories() + "'" +
            "}";
    }
}
