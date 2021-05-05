package com.hypercell.axa.quotation.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A RFQProductsInfo.
 */
@Entity
@Table(name = "rfq_products_info")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class RFQProductsInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "product_id")
    private Long productId;

    @Column(name = "product_attr_id")
    private Long productAttrId;

    @Column(name = "attribute_name")
    private String attributeName;

    @Column(name = "attribute_value")
    private String attributeValue;

    @OneToMany(mappedBy = "rFQProductsInfo")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "rFQProductsInfo" }, allowSetters = true)
    private Set<RFQProductsInfoValueList> rFQProductsInfoValueLists = new HashSet<>();

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

    public RFQProductsInfo id(Long id) {
        this.id = id;
        return this;
    }

    public Long getProductId() {
        return this.productId;
    }

    public RFQProductsInfo productId(Long productId) {
        this.productId = productId;
        return this;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getProductAttrId() {
        return this.productAttrId;
    }

    public RFQProductsInfo productAttrId(Long productAttrId) {
        this.productAttrId = productAttrId;
        return this;
    }

    public void setProductAttrId(Long productAttrId) {
        this.productAttrId = productAttrId;
    }

    public String getAttributeName() {
        return this.attributeName;
    }

    public RFQProductsInfo attributeName(String attributeName) {
        this.attributeName = attributeName;
        return this;
    }

    public void setAttributeName(String attributeName) {
        this.attributeName = attributeName;
    }

    public String getAttributeValue() {
        return this.attributeValue;
    }

    public RFQProductsInfo attributeValue(String attributeValue) {
        this.attributeValue = attributeValue;
        return this;
    }

    public void setAttributeValue(String attributeValue) {
        this.attributeValue = attributeValue;
    }

    public Set<RFQProductsInfoValueList> getRFQProductsInfoValueLists() {
        return this.rFQProductsInfoValueLists;
    }

    public RFQProductsInfo rFQProductsInfoValueLists(Set<RFQProductsInfoValueList> rFQProductsInfoValueLists) {
        this.setRFQProductsInfoValueLists(rFQProductsInfoValueLists);
        return this;
    }

    public RFQProductsInfo addRFQProductsInfoValueList(RFQProductsInfoValueList rFQProductsInfoValueList) {
        this.rFQProductsInfoValueLists.add(rFQProductsInfoValueList);
        rFQProductsInfoValueList.setRFQProductsInfo(this);
        return this;
    }

    public RFQProductsInfo removeRFQProductsInfoValueList(RFQProductsInfoValueList rFQProductsInfoValueList) {
        this.rFQProductsInfoValueLists.remove(rFQProductsInfoValueList);
        rFQProductsInfoValueList.setRFQProductsInfo(null);
        return this;
    }

    public void setRFQProductsInfoValueLists(Set<RFQProductsInfoValueList> rFQProductsInfoValueLists) {
        if (this.rFQProductsInfoValueLists != null) {
            this.rFQProductsInfoValueLists.forEach(i -> i.setRFQProductsInfo(null));
        }
        if (rFQProductsInfoValueLists != null) {
            rFQProductsInfoValueLists.forEach(i -> i.setRFQProductsInfo(this));
        }
        this.rFQProductsInfoValueLists = rFQProductsInfoValueLists;
    }

    public RequestForQuotation getRequestForQuotation() {
        return this.requestForQuotation;
    }

    public RFQProductsInfo requestForQuotation(RequestForQuotation requestForQuotation) {
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
        if (!(o instanceof RFQProductsInfo)) {
            return false;
        }
        return id != null && id.equals(((RFQProductsInfo) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RFQProductsInfo{" +
            "id=" + getId() +
            ", productId=" + getProductId() +
            ", productAttrId=" + getProductAttrId() +
            ", attributeName='" + getAttributeName() + "'" +
            ", attributeValue='" + getAttributeValue() + "'" +
            "}";
    }
}
