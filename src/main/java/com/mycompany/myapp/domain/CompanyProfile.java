package com.mycompany.myapp.domain;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A CompanyProfile.
 */
@Entity
@Table(name = "company_profile")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CompanyProfile implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull
    @Size(max = 600)
    @Column(name = "email", length = 600, nullable = false)
    private String email;

    @NotNull
    @Size(max = 600)
    @Column(name = "address", length = 600, nullable = false)
    private String address;

    @NotNull
    @Column(name = "contact_number", nullable = false)
    private Long contactNumber;

    @NotNull
    @Size(max = 600)
    @Column(name = "linkedin_profile", length = 600, nullable = false)
    private String linkedinProfile;

    @NotNull
    @Size(max = 600)
    @Column(name = "location", length = 600, nullable = false)
    private String location;

    @NotNull
    @Column(name = "technology", nullable = false)
    private String technology;

    @NotNull
    @Column(name = "ctc", nullable = false)
    private String ctc;

    @NotNull
    @Size(max = 600)
    @Column(name = "overview", length = 600, nullable = false)
    private String overview;

    @NotNull
    @Column(name = "bond", nullable = false)
    private Integer bond;

    @NotNull
    @Size(max = 600)
    @Column(name = "minimum_criteria", length = 600, nullable = false)
    private String minimumCriteria;

    @NotNull
    @Column(name = "type", nullable = false)
    private String type;

    @NotNull
    @Column(name = "no_of_openings", nullable = false)
    private Integer noOfOpenings;

    @ManyToOne
    private User user;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public CompanyProfile id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return this.email;
    }

    public CompanyProfile email(String email) {
        this.setEmail(email);
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return this.address;
    }

    public CompanyProfile address(String address) {
        this.setAddress(address);
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Long getContactNumber() {
        return this.contactNumber;
    }

    public CompanyProfile contactNumber(Long contactNumber) {
        this.setContactNumber(contactNumber);
        return this;
    }

    public void setContactNumber(Long contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getLinkedinProfile() {
        return this.linkedinProfile;
    }

    public CompanyProfile linkedinProfile(String linkedinProfile) {
        this.setLinkedinProfile(linkedinProfile);
        return this;
    }

    public void setLinkedinProfile(String linkedinProfile) {
        this.linkedinProfile = linkedinProfile;
    }

    public String getLocation() {
        return this.location;
    }

    public CompanyProfile location(String location) {
        this.setLocation(location);
        return this;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getTechnology() {
        return this.technology;
    }

    public CompanyProfile technology(String technology) {
        this.setTechnology(technology);
        return this;
    }

    public void setTechnology(String technology) {
        this.technology = technology;
    }

    public String getCtc() {
        return this.ctc;
    }

    public CompanyProfile ctc(String ctc) {
        this.setCtc(ctc);
        return this;
    }

    public void setCtc(String ctc) {
        this.ctc = ctc;
    }

    public String getOverview() {
        return this.overview;
    }

    public CompanyProfile overview(String overview) {
        this.setOverview(overview);
        return this;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public Integer getBond() {
        return this.bond;
    }

    public CompanyProfile bond(Integer bond) {
        this.setBond(bond);
        return this;
    }

    public void setBond(Integer bond) {
        this.bond = bond;
    }

    public String getMinimumCriteria() {
        return this.minimumCriteria;
    }

    public CompanyProfile minimumCriteria(String minimumCriteria) {
        this.setMinimumCriteria(minimumCriteria);
        return this;
    }

    public void setMinimumCriteria(String minimumCriteria) {
        this.minimumCriteria = minimumCriteria;
    }

    public String getType() {
        return this.type;
    }

    public CompanyProfile type(String type) {
        this.setType(type);
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getNoOfOpenings() {
        return this.noOfOpenings;
    }

    public CompanyProfile noOfOpenings(Integer noOfOpenings) {
        this.setNoOfOpenings(noOfOpenings);
        return this;
    }

    public void setNoOfOpenings(Integer noOfOpenings) {
        this.noOfOpenings = noOfOpenings;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public CompanyProfile user(User user) {
        this.setUser(user);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CompanyProfile)) {
            return false;
        }
        return id != null && id.equals(((CompanyProfile) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CompanyProfile{" +
            "id=" + getId() +
            ", email='" + getEmail() + "'" +
            ", address='" + getAddress() + "'" +
            ", contactNumber=" + getContactNumber() +
            ", linkedinProfile='" + getLinkedinProfile() + "'" +
            ", location='" + getLocation() + "'" +
            ", technology='" + getTechnology() + "'" +
            ", ctc='" + getCtc() + "'" +
            ", overview='" + getOverview() + "'" +
            ", bond=" + getBond() +
            ", minimumCriteria='" + getMinimumCriteria() + "'" +
            ", type='" + getType() + "'" +
            ", noOfOpenings=" + getNoOfOpenings() +
            "}";
    }
}
