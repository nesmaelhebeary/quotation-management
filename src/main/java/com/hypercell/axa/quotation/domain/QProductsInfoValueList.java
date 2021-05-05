package com.hypercell.axa.quotation.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A QProductsInfoValueList.
 */
@Entity
@Table(name = "q_products_info_value_list")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class QProductsInfoValueList implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "product_value_attr_id")
    private Long productValueAttrId;

    @Column(name = "attribute_value")
    private String attributeValue;

    @ManyToOne
    @JsonIgnoreProperties(value = { "qProductsInfoValueLists", "quotation" }, allowSetters = true)
    private QProductsInfo qProductsInfo;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public QProductsInfoValueList id(Long id) {
        this.id = id;
        return this;
    }

    public Long getProductValueAttrId() {
        return this.productValueAttrId;
    }

    public QProductsInfoValueList productValueAttrId(Long productValueAttrId) {
        this.productValueAttrId = productValueAttrId;
        return this;
    }

    public void setProductValueAttrId(Long productValueAttrId) {
        this.productValueAttrId = productValueAttrId;
    }

    public String getAttributeValue() {
        return this.attributeValue;
    }

    public QProductsInfoValueList attributeValue(String attributeValue) {
        this.attributeValue = attributeValue;
        return this;
    }

    public void setAttributeValue(String attributeValue) {
        this.attributeValue = attributeValue;
    }

    public QProductsInfo getQProductsInfo() {
        return this.qProductsInfo;
    }

    public QProductsInfoValueList qProductsInfo(QProductsInfo qProductsInfo) {
        this.setQProductsInfo(qProductsInfo);
        return this;
    }

    public void setQProductsInfo(QProductsInfo qProductsInfo) {
        this.qProductsInfo = qProductsInfo;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof QProductsInfoValueList)) {
            return false;
        }
        return id != null && id.equals(((QProductsInfoValueList) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "QProductsInfoValueList{" +
            "id=" + getId() +
            ", productValueAttrId=" + getProductValueAttrId() +
            ", attributeValue='" + getAttributeValue() + "'" +
            "}";
    }
}
