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
 * A QProducts.
 */
@Entity
@Table(name = "q_products")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class QProducts implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "quotation_id")
    private Long quotationId;

    @Column(name = "product_id")
    private Long productId;

    @Enumerated(EnumType.STRING)
    @Column(name = "cover_type")
    private CoverType coverType;

    @OneToMany(mappedBy = "qProducts")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "qProductsItemsSections", "qProductsItemsValues", "qProducts" }, allowSetters = true)
    private Set<QProductsItems> qProductsItems = new HashSet<>();

    @OneToMany(mappedBy = "qProducts")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "qProducts" }, allowSetters = true)
    private Set<QProductsSections> qProductsSections = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(
        value = { "siteSurveyDetails", "qDocuments", "qProducts", "qProductsInfos", "requestForQuotation" },
        allowSetters = true
    )
    private Quotation quotation;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public QProducts id(Long id) {
        this.id = id;
        return this;
    }

    public Long getQuotationId() {
        return this.quotationId;
    }

    public QProducts quotationId(Long quotationId) {
        this.quotationId = quotationId;
        return this;
    }

    public void setQuotationId(Long quotationId) {
        this.quotationId = quotationId;
    }

    public Long getProductId() {
        return this.productId;
    }

    public QProducts productId(Long productId) {
        this.productId = productId;
        return this;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public CoverType getCoverType() {
        return this.coverType;
    }

    public QProducts coverType(CoverType coverType) {
        this.coverType = coverType;
        return this;
    }

    public void setCoverType(CoverType coverType) {
        this.coverType = coverType;
    }

    public Set<QProductsItems> getQProductsItems() {
        return this.qProductsItems;
    }

    public QProducts qProductsItems(Set<QProductsItems> qProductsItems) {
        this.setQProductsItems(qProductsItems);
        return this;
    }

    public QProducts addQProductsItems(QProductsItems qProductsItems) {
        this.qProductsItems.add(qProductsItems);
        qProductsItems.setQProducts(this);
        return this;
    }

    public QProducts removeQProductsItems(QProductsItems qProductsItems) {
        this.qProductsItems.remove(qProductsItems);
        qProductsItems.setQProducts(null);
        return this;
    }

    public void setQProductsItems(Set<QProductsItems> qProductsItems) {
        if (this.qProductsItems != null) {
            this.qProductsItems.forEach(i -> i.setQProducts(null));
        }
        if (qProductsItems != null) {
            qProductsItems.forEach(i -> i.setQProducts(this));
        }
        this.qProductsItems = qProductsItems;
    }

    public Set<QProductsSections> getQProductsSections() {
        return this.qProductsSections;
    }

    public QProducts qProductsSections(Set<QProductsSections> qProductsSections) {
        this.setQProductsSections(qProductsSections);
        return this;
    }

    public QProducts addQProductsSections(QProductsSections qProductsSections) {
        this.qProductsSections.add(qProductsSections);
        qProductsSections.setQProducts(this);
        return this;
    }

    public QProducts removeQProductsSections(QProductsSections qProductsSections) {
        this.qProductsSections.remove(qProductsSections);
        qProductsSections.setQProducts(null);
        return this;
    }

    public void setQProductsSections(Set<QProductsSections> qProductsSections) {
        if (this.qProductsSections != null) {
            this.qProductsSections.forEach(i -> i.setQProducts(null));
        }
        if (qProductsSections != null) {
            qProductsSections.forEach(i -> i.setQProducts(this));
        }
        this.qProductsSections = qProductsSections;
    }

    public Quotation getQuotation() {
        return this.quotation;
    }

    public QProducts quotation(Quotation quotation) {
        this.setQuotation(quotation);
        return this;
    }

    public void setQuotation(Quotation quotation) {
        this.quotation = quotation;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof QProducts)) {
            return false;
        }
        return id != null && id.equals(((QProducts) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "QProducts{" +
            "id=" + getId() +
            ", quotationId=" + getQuotationId() +
            ", productId=" + getProductId() +
            ", coverType='" + getCoverType() + "'" +
            "}";
    }
}
