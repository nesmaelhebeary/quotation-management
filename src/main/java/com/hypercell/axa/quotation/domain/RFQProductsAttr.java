package com.hypercell.axa.quotation.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A RFQProductsAttr.
 */
@Entity
@Table(name = "rfq_products_attr")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class RFQProductsAttr implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "attribute_name")
    private String attributeName;

    @Column(name = "attribute_value")
    private String attributeValue;

    @ManyToOne
    @JsonIgnoreProperties(value = { "rFQProductsAttrs", "rFQProductsSections", "requestForQuotation" }, allowSetters = true)
    private RFQProducts rFQProducts;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public RFQProductsAttr id(Long id) {
        this.id = id;
        return this;
    }

    public String getAttributeName() {
        return this.attributeName;
    }

    public RFQProductsAttr attributeName(String attributeName) {
        this.attributeName = attributeName;
        return this;
    }

    public void setAttributeName(String attributeName) {
        this.attributeName = attributeName;
    }

    public String getAttributeValue() {
        return this.attributeValue;
    }

    public RFQProductsAttr attributeValue(String attributeValue) {
        this.attributeValue = attributeValue;
        return this;
    }

    public void setAttributeValue(String attributeValue) {
        this.attributeValue = attributeValue;
    }

    public RFQProducts getRFQProducts() {
        return this.rFQProducts;
    }

    public RFQProductsAttr rFQProducts(RFQProducts rFQProducts) {
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
        if (!(o instanceof RFQProductsAttr)) {
            return false;
        }
        return id != null && id.equals(((RFQProductsAttr) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RFQProductsAttr{" +
            "id=" + getId() +
            ", attributeName='" + getAttributeName() + "'" +
            ", attributeValue='" + getAttributeValue() + "'" +
            "}";
    }
}
