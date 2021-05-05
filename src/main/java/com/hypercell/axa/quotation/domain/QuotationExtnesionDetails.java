package com.hypercell.axa.quotation.domain;

import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A QuotationExtnesionDetails.
 */
@Entity
@Table(name = "quotation_extnesion_details")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class QuotationExtnesionDetails implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "quota_extension_id")
    private Long quotaExtensionId;

    @Column(name = "percentage_item_id")
    private Long percentageItemId;

    @Column(name = "percentage_item_value")
    private Double percentageItemValue;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public QuotationExtnesionDetails id(Long id) {
        this.id = id;
        return this;
    }

    public Long getQuotaExtensionId() {
        return this.quotaExtensionId;
    }

    public QuotationExtnesionDetails quotaExtensionId(Long quotaExtensionId) {
        this.quotaExtensionId = quotaExtensionId;
        return this;
    }

    public void setQuotaExtensionId(Long quotaExtensionId) {
        this.quotaExtensionId = quotaExtensionId;
    }

    public Long getPercentageItemId() {
        return this.percentageItemId;
    }

    public QuotationExtnesionDetails percentageItemId(Long percentageItemId) {
        this.percentageItemId = percentageItemId;
        return this;
    }

    public void setPercentageItemId(Long percentageItemId) {
        this.percentageItemId = percentageItemId;
    }

    public Double getPercentageItemValue() {
        return this.percentageItemValue;
    }

    public QuotationExtnesionDetails percentageItemValue(Double percentageItemValue) {
        this.percentageItemValue = percentageItemValue;
        return this;
    }

    public void setPercentageItemValue(Double percentageItemValue) {
        this.percentageItemValue = percentageItemValue;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof QuotationExtnesionDetails)) {
            return false;
        }
        return id != null && id.equals(((QuotationExtnesionDetails) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "QuotationExtnesionDetails{" +
            "id=" + getId() +
            ", quotaExtensionId=" + getQuotaExtensionId() +
            ", percentageItemId=" + getPercentageItemId() +
            ", percentageItemValue=" + getPercentageItemValue() +
            "}";
    }
}
