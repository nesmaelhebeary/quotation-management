package com.hypercell.axa.quotation.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A QProductsInfo.
 */
@Entity
@Table(name = "q_products_info")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class QProductsInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "quotation_id")
    private Long quotationId;

    @Column(name = "product_id")
    private Long productId;

    @Column(name = "product_attr_id")
    private Long productAttrId;

    @Column(name = "attribute_name")
    private String attributeName;

    @Column(name = "attribute_value")
    private String attributeValue;

    @OneToMany(mappedBy = "qProductsInfo")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "qProductsInfo" }, allowSetters = true)
    private Set<QProductsInfoValueList> qProductsInfoValueLists = new HashSet<>();

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

    public QProductsInfo id(Long id) {
        this.id = id;
        return this;
    }

    public Long getQuotationId() {
        return this.quotationId;
    }

    public QProductsInfo quotationId(Long quotationId) {
        this.quotationId = quotationId;
        return this;
    }

    public void setQuotationId(Long quotationId) {
        this.quotationId = quotationId;
    }

    public Long getProductId() {
        return this.productId;
    }

    public QProductsInfo productId(Long productId) {
        this.productId = productId;
        return this;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getProductAttrId() {
        return this.productAttrId;
    }

    public QProductsInfo productAttrId(Long productAttrId) {
        this.productAttrId = productAttrId;
        return this;
    }

    public void setProductAttrId(Long productAttrId) {
        this.productAttrId = productAttrId;
    }

    public String getAttributeName() {
        return this.attributeName;
    }

    public QProductsInfo attributeName(String attributeName) {
        this.attributeName = attributeName;
        return this;
    }

    public void setAttributeName(String attributeName) {
        this.attributeName = attributeName;
    }

    public String getAttributeValue() {
        return this.attributeValue;
    }

    public QProductsInfo attributeValue(String attributeValue) {
        this.attributeValue = attributeValue;
        return this;
    }

    public void setAttributeValue(String attributeValue) {
        this.attributeValue = attributeValue;
    }

    public Set<QProductsInfoValueList> getQProductsInfoValueLists() {
        return this.qProductsInfoValueLists;
    }

    public QProductsInfo qProductsInfoValueLists(Set<QProductsInfoValueList> qProductsInfoValueLists) {
        this.setQProductsInfoValueLists(qProductsInfoValueLists);
        return this;
    }

    public QProductsInfo addQProductsInfoValueList(QProductsInfoValueList qProductsInfoValueList) {
        this.qProductsInfoValueLists.add(qProductsInfoValueList);
        qProductsInfoValueList.setQProductsInfo(this);
        return this;
    }

    public QProductsInfo removeQProductsInfoValueList(QProductsInfoValueList qProductsInfoValueList) {
        this.qProductsInfoValueLists.remove(qProductsInfoValueList);
        qProductsInfoValueList.setQProductsInfo(null);
        return this;
    }

    public void setQProductsInfoValueLists(Set<QProductsInfoValueList> qProductsInfoValueLists) {
        if (this.qProductsInfoValueLists != null) {
            this.qProductsInfoValueLists.forEach(i -> i.setQProductsInfo(null));
        }
        if (qProductsInfoValueLists != null) {
            qProductsInfoValueLists.forEach(i -> i.setQProductsInfo(this));
        }
        this.qProductsInfoValueLists = qProductsInfoValueLists;
    }

    public Quotation getQuotation() {
        return this.quotation;
    }

    public QProductsInfo quotation(Quotation quotation) {
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
        if (!(o instanceof QProductsInfo)) {
            return false;
        }
        return id != null && id.equals(((QProductsInfo) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "QProductsInfo{" +
            "id=" + getId() +
            ", quotationId=" + getQuotationId() +
            ", productId=" + getProductId() +
            ", productAttrId=" + getProductAttrId() +
            ", attributeName='" + getAttributeName() + "'" +
            ", attributeValue='" + getAttributeValue() + "'" +
            "}";
    }
}
