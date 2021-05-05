package com.hypercell.axa.quotation.domain;

import com.hypercell.axa.quotation.domain.enumeration.ExntensionPercentageType;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A QuotationExtensions.
 */
@Entity
@Table(name = "quotation_extensions")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class QuotationExtensions implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "quotation_id")
    private Long quotationId;

    @Column(name = "extension_id")
    private Long extensionId;

    @Column(name = "limit")
    private Double limit;

    @Column(name = "percentage")
    private Double percentage;

    @Column(name = "mpl_value")
    private Double mplValue;

    @Column(name = "extension_ar")
    private String extensionAr;

    @Column(name = "extension_en")
    private String extensionEn;

    @Enumerated(EnumType.STRING)
    @Column(name = "exntension_percentage_type")
    private ExntensionPercentageType exntensionPercentageType;

    @Column(name = "modified")
    private Boolean modified;

    @Column(name = "modified_by")
    private String modifiedBy;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public QuotationExtensions id(Long id) {
        this.id = id;
        return this;
    }

    public Long getQuotationId() {
        return this.quotationId;
    }

    public QuotationExtensions quotationId(Long quotationId) {
        this.quotationId = quotationId;
        return this;
    }

    public void setQuotationId(Long quotationId) {
        this.quotationId = quotationId;
    }

    public Long getExtensionId() {
        return this.extensionId;
    }

    public QuotationExtensions extensionId(Long extensionId) {
        this.extensionId = extensionId;
        return this;
    }

    public void setExtensionId(Long extensionId) {
        this.extensionId = extensionId;
    }

    public Double getLimit() {
        return this.limit;
    }

    public QuotationExtensions limit(Double limit) {
        this.limit = limit;
        return this;
    }

    public void setLimit(Double limit) {
        this.limit = limit;
    }

    public Double getPercentage() {
        return this.percentage;
    }

    public QuotationExtensions percentage(Double percentage) {
        this.percentage = percentage;
        return this;
    }

    public void setPercentage(Double percentage) {
        this.percentage = percentage;
    }

    public Double getMplValue() {
        return this.mplValue;
    }

    public QuotationExtensions mplValue(Double mplValue) {
        this.mplValue = mplValue;
        return this;
    }

    public void setMplValue(Double mplValue) {
        this.mplValue = mplValue;
    }

    public String getExtensionAr() {
        return this.extensionAr;
    }

    public QuotationExtensions extensionAr(String extensionAr) {
        this.extensionAr = extensionAr;
        return this;
    }

    public void setExtensionAr(String extensionAr) {
        this.extensionAr = extensionAr;
    }

    public String getExtensionEn() {
        return this.extensionEn;
    }

    public QuotationExtensions extensionEn(String extensionEn) {
        this.extensionEn = extensionEn;
        return this;
    }

    public void setExtensionEn(String extensionEn) {
        this.extensionEn = extensionEn;
    }

    public ExntensionPercentageType getExntensionPercentageType() {
        return this.exntensionPercentageType;
    }

    public QuotationExtensions exntensionPercentageType(ExntensionPercentageType exntensionPercentageType) {
        this.exntensionPercentageType = exntensionPercentageType;
        return this;
    }

    public void setExntensionPercentageType(ExntensionPercentageType exntensionPercentageType) {
        this.exntensionPercentageType = exntensionPercentageType;
    }

    public Boolean getModified() {
        return this.modified;
    }

    public QuotationExtensions modified(Boolean modified) {
        this.modified = modified;
        return this;
    }

    public void setModified(Boolean modified) {
        this.modified = modified;
    }

    public String getModifiedBy() {
        return this.modifiedBy;
    }

    public QuotationExtensions modifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
        return this;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof QuotationExtensions)) {
            return false;
        }
        return id != null && id.equals(((QuotationExtensions) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "QuotationExtensions{" +
            "id=" + getId() +
            ", quotationId=" + getQuotationId() +
            ", extensionId=" + getExtensionId() +
            ", limit=" + getLimit() +
            ", percentage=" + getPercentage() +
            ", mplValue=" + getMplValue() +
            ", extensionAr='" + getExtensionAr() + "'" +
            ", extensionEn='" + getExtensionEn() + "'" +
            ", exntensionPercentageType='" + getExntensionPercentageType() + "'" +
            ", modified='" + getModified() + "'" +
            ", modifiedBy='" + getModifiedBy() + "'" +
            "}";
    }
}
