package com.hypercell.axa.quotation.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A RFQProductsExtensions.
 */
@Entity
@Table(name = "rfq_products_extensions")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class RFQProductsExtensions implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "extension_name")
    private String extensionName;

    @Column(name = "rfq_id")
    private Long rfqId;

    @Column(name = "extension_id")
    private Long extensionId;

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

    public RFQProductsExtensions id(Long id) {
        this.id = id;
        return this;
    }

    public String getExtensionName() {
        return this.extensionName;
    }

    public RFQProductsExtensions extensionName(String extensionName) {
        this.extensionName = extensionName;
        return this;
    }

    public void setExtensionName(String extensionName) {
        this.extensionName = extensionName;
    }

    public Long getRfqId() {
        return this.rfqId;
    }

    public RFQProductsExtensions rfqId(Long rfqId) {
        this.rfqId = rfqId;
        return this;
    }

    public void setRfqId(Long rfqId) {
        this.rfqId = rfqId;
    }

    public Long getExtensionId() {
        return this.extensionId;
    }

    public RFQProductsExtensions extensionId(Long extensionId) {
        this.extensionId = extensionId;
        return this;
    }

    public void setExtensionId(Long extensionId) {
        this.extensionId = extensionId;
    }

    public RFQProducts getRFQProducts() {
        return this.rFQProducts;
    }

    public RFQProductsExtensions rFQProducts(RFQProducts rFQProducts) {
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
        if (!(o instanceof RFQProductsExtensions)) {
            return false;
        }
        return id != null && id.equals(((RFQProductsExtensions) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RFQProductsExtensions{" +
            "id=" + getId() +
            ", extensionName='" + getExtensionName() + "'" +
            ", rfqId=" + getRfqId() +
            ", extensionId=" + getExtensionId() +
            "}";
    }
}
