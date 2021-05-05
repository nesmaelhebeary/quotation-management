package com.hypercell.axa.quotation.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A RFQProductsDeductibles.
 */
@Entity
@Table(name = "rfq_products_deductibles")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class RFQProductsDeductibles implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "deductible_description")
    private String deductibleDescription;

    @Column(name = "rfq_id")
    private Long rfqId;

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

    public RFQProductsDeductibles id(Long id) {
        this.id = id;
        return this;
    }

    public String getDeductibleDescription() {
        return this.deductibleDescription;
    }

    public RFQProductsDeductibles deductibleDescription(String deductibleDescription) {
        this.deductibleDescription = deductibleDescription;
        return this;
    }

    public void setDeductibleDescription(String deductibleDescription) {
        this.deductibleDescription = deductibleDescription;
    }

    public Long getRfqId() {
        return this.rfqId;
    }

    public RFQProductsDeductibles rfqId(Long rfqId) {
        this.rfqId = rfqId;
        return this;
    }

    public void setRfqId(Long rfqId) {
        this.rfqId = rfqId;
    }

    public RFQProducts getRFQProducts() {
        return this.rFQProducts;
    }

    public RFQProductsDeductibles rFQProducts(RFQProducts rFQProducts) {
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
        if (!(o instanceof RFQProductsDeductibles)) {
            return false;
        }
        return id != null && id.equals(((RFQProductsDeductibles) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RFQProductsDeductibles{" +
            "id=" + getId() +
            ", deductibleDescription='" + getDeductibleDescription() + "'" +
            ", rfqId=" + getRfqId() +
            "}";
    }
}
