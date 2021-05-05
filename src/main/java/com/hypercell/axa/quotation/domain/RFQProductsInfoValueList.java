package com.hypercell.axa.quotation.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A RFQProductsInfoValueList.
 */
@Entity
@Table(name = "rfq_products_info_value_list")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class RFQProductsInfoValueList implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "attribute_value")
    private String attributeValue;

    @ManyToOne
    @JsonIgnoreProperties(value = { "rFQProductsInfoValueLists", "requestForQuotation" }, allowSetters = true)
    private RFQProductsInfo rFQProductsInfo;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public RFQProductsInfoValueList id(Long id) {
        this.id = id;
        return this;
    }

    public String getAttributeValue() {
        return this.attributeValue;
    }

    public RFQProductsInfoValueList attributeValue(String attributeValue) {
        this.attributeValue = attributeValue;
        return this;
    }

    public void setAttributeValue(String attributeValue) {
        this.attributeValue = attributeValue;
    }

    public RFQProductsInfo getRFQProductsInfo() {
        return this.rFQProductsInfo;
    }

    public RFQProductsInfoValueList rFQProductsInfo(RFQProductsInfo rFQProductsInfo) {
        this.setRFQProductsInfo(rFQProductsInfo);
        return this;
    }

    public void setRFQProductsInfo(RFQProductsInfo rFQProductsInfo) {
        this.rFQProductsInfo = rFQProductsInfo;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RFQProductsInfoValueList)) {
            return false;
        }
        return id != null && id.equals(((RFQProductsInfoValueList) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RFQProductsInfoValueList{" +
            "id=" + getId() +
            ", attributeValue='" + getAttributeValue() + "'" +
            "}";
    }
}
