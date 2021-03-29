package com.hypercell.axa.quotation.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A RFQSectionsItems.
 */
@Entity
@Table(name = "rfq_sections_items")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class RFQSectionsItems implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "sum_insured")
    private Double sumInsured;

    @Column(name = "name")
    private String name;

    @Column(name = "currency")
    private String currency;

    @Column(name = "description")
    private String description;

    @ManyToOne
    @JsonIgnoreProperties(value = { "rFQSectionsItems", "rFQProducts" }, allowSetters = true)
    private RFQProductsSections rFQProductsSections;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public RFQSectionsItems id(Long id) {
        this.id = id;
        return this;
    }

    public Double getSumInsured() {
        return this.sumInsured;
    }

    public RFQSectionsItems sumInsured(Double sumInsured) {
        this.sumInsured = sumInsured;
        return this;
    }

    public void setSumInsured(Double sumInsured) {
        this.sumInsured = sumInsured;
    }

    public String getName() {
        return this.name;
    }

    public RFQSectionsItems name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCurrency() {
        return this.currency;
    }

    public RFQSectionsItems currency(String currency) {
        this.currency = currency;
        return this;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getDescription() {
        return this.description;
    }

    public RFQSectionsItems description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public RFQProductsSections getRFQProductsSections() {
        return this.rFQProductsSections;
    }

    public RFQSectionsItems rFQProductsSections(RFQProductsSections rFQProductsSections) {
        this.setRFQProductsSections(rFQProductsSections);
        return this;
    }

    public void setRFQProductsSections(RFQProductsSections rFQProductsSections) {
        this.rFQProductsSections = rFQProductsSections;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RFQSectionsItems)) {
            return false;
        }
        return id != null && id.equals(((RFQSectionsItems) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RFQSectionsItems{" +
            "id=" + getId() +
            ", sumInsured=" + getSumInsured() +
            ", name='" + getName() + "'" +
            ", currency='" + getCurrency() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
