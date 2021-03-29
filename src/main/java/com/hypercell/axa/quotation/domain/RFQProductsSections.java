package com.hypercell.axa.quotation.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A RFQProductsSections.
 */
@Entity
@Table(name = "rfq_products_sections")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class RFQProductsSections implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "section_name")
    private String sectionName;

    @Column(name = "sum_insured")
    private Double sumInsured;

    @OneToMany(mappedBy = "rFQProductsSections")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "rFQProductsSections" }, allowSetters = true)
    private Set<RFQSectionsItems> rFQSectionsItems = new HashSet<>();

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

    public RFQProductsSections id(Long id) {
        this.id = id;
        return this;
    }

    public String getSectionName() {
        return this.sectionName;
    }

    public RFQProductsSections sectionName(String sectionName) {
        this.sectionName = sectionName;
        return this;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }

    public Double getSumInsured() {
        return this.sumInsured;
    }

    public RFQProductsSections sumInsured(Double sumInsured) {
        this.sumInsured = sumInsured;
        return this;
    }

    public void setSumInsured(Double sumInsured) {
        this.sumInsured = sumInsured;
    }

    public Set<RFQSectionsItems> getRFQSectionsItems() {
        return this.rFQSectionsItems;
    }

    public RFQProductsSections rFQSectionsItems(Set<RFQSectionsItems> rFQSectionsItems) {
        this.setRFQSectionsItems(rFQSectionsItems);
        return this;
    }

    public RFQProductsSections addRFQSectionsItems(RFQSectionsItems rFQSectionsItems) {
        this.rFQSectionsItems.add(rFQSectionsItems);
        rFQSectionsItems.setRFQProductsSections(this);
        return this;
    }

    public RFQProductsSections removeRFQSectionsItems(RFQSectionsItems rFQSectionsItems) {
        this.rFQSectionsItems.remove(rFQSectionsItems);
        rFQSectionsItems.setRFQProductsSections(null);
        return this;
    }

    public void setRFQSectionsItems(Set<RFQSectionsItems> rFQSectionsItems) {
        if (this.rFQSectionsItems != null) {
            this.rFQSectionsItems.forEach(i -> i.setRFQProductsSections(null));
        }
        if (rFQSectionsItems != null) {
            rFQSectionsItems.forEach(i -> i.setRFQProductsSections(this));
        }
        this.rFQSectionsItems = rFQSectionsItems;
    }

    public RFQProducts getRFQProducts() {
        return this.rFQProducts;
    }

    public RFQProductsSections rFQProducts(RFQProducts rFQProducts) {
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
        if (!(o instanceof RFQProductsSections)) {
            return false;
        }
        return id != null && id.equals(((RFQProductsSections) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RFQProductsSections{" +
            "id=" + getId() +
            ", sectionName='" + getSectionName() + "'" +
            ", sumInsured=" + getSumInsured() +
            "}";
    }
}
