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

    @Column(name = "rfq_id")
    private Long rfqId;

    @Column(name = "product_id")
    private Long productId;

    @Enumerated(EnumType.STRING)
    @Column(name = "cover_type")
    private CoverType coverType;

    @Column(name = "sum_insured")
    private Double sumInsured;

    @OneToMany(mappedBy = "rFQProducts")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "rFQProducts" }, allowSetters = true)
    private Set<RFQProductsAttr> rFQProductsAttrs = new HashSet<>();

    @OneToMany(mappedBy = "rFQProducts")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "rFQSectionsItems", "rFQProducts" }, allowSetters = true)
    private Set<RFQProductsSections> rFQProductsSections = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "rFQClaims", "rFQDocuments", "rFQProducts" }, allowSetters = true)
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

    public Long getRfqId() {
        return this.rfqId;
    }

    public RFQProducts rfqId(Long rfqId) {
        this.rfqId = rfqId;
        return this;
    }

    public void setRfqId(Long rfqId) {
        this.rfqId = rfqId;
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

    public Double getSumInsured() {
        return this.sumInsured;
    }

    public RFQProducts sumInsured(Double sumInsured) {
        this.sumInsured = sumInsured;
        return this;
    }

    public void setSumInsured(Double sumInsured) {
        this.sumInsured = sumInsured;
    }

    public Set<RFQProductsAttr> getRFQProductsAttrs() {
        return this.rFQProductsAttrs;
    }

    public RFQProducts rFQProductsAttrs(Set<RFQProductsAttr> rFQProductsAttrs) {
        this.setRFQProductsAttrs(rFQProductsAttrs);
        return this;
    }

    public RFQProducts addRFQProductsAttr(RFQProductsAttr rFQProductsAttr) {
        this.rFQProductsAttrs.add(rFQProductsAttr);
        rFQProductsAttr.setRFQProducts(this);
        return this;
    }

    public RFQProducts removeRFQProductsAttr(RFQProductsAttr rFQProductsAttr) {
        this.rFQProductsAttrs.remove(rFQProductsAttr);
        rFQProductsAttr.setRFQProducts(null);
        return this;
    }

    public void setRFQProductsAttrs(Set<RFQProductsAttr> rFQProductsAttrs) {
        if (this.rFQProductsAttrs != null) {
            this.rFQProductsAttrs.forEach(i -> i.setRFQProducts(null));
        }
        if (rFQProductsAttrs != null) {
            rFQProductsAttrs.forEach(i -> i.setRFQProducts(this));
        }
        this.rFQProductsAttrs = rFQProductsAttrs;
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
            ", rfqId=" + getRfqId() +
            ", productId=" + getProductId() +
            ", coverType='" + getCoverType() + "'" +
            ", sumInsured=" + getSumInsured() +
            "}";
    }
}
