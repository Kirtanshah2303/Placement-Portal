package com.mycompany.myapp.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.CompanyProfile} entity.
 */
public class CompanyProfileDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 600)
    private String email;

    @NotNull
    @Size(max = 600)
    private String address;

    @NotNull
    private Long contactNumber;

    @NotNull
    @Size(max = 600)
    private String linkedinProfile;

    @NotNull
    @Size(max = 600)
    private String location;

    @NotNull
    private String technology;

    @NotNull
    private String ctc;

    @NotNull
    @Size(max = 600)
    private String overview;

    @NotNull
    private Integer bond;

    @NotNull
    @Size(max = 600)
    private String minimumCriteria;

    @NotNull
    private String type;

    @NotNull
    private Integer noOfOpenings;

    private UserDTO user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Long getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(Long contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getLinkedinProfile() {
        return linkedinProfile;
    }

    public void setLinkedinProfile(String linkedinProfile) {
        this.linkedinProfile = linkedinProfile;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getTechnology() {
        return technology;
    }

    public void setTechnology(String technology) {
        this.technology = technology;
    }

    public String getCtc() {
        return ctc;
    }

    public void setCtc(String ctc) {
        this.ctc = ctc;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public Integer getBond() {
        return bond;
    }

    public void setBond(Integer bond) {
        this.bond = bond;
    }

    public String getMinimumCriteria() {
        return minimumCriteria;
    }

    public void setMinimumCriteria(String minimumCriteria) {
        this.minimumCriteria = minimumCriteria;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getNoOfOpenings() {
        return noOfOpenings;
    }

    public void setNoOfOpenings(Integer noOfOpenings) {
        this.noOfOpenings = noOfOpenings;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CompanyProfileDTO)) {
            return false;
        }

        CompanyProfileDTO companyProfileDTO = (CompanyProfileDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, companyProfileDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CompanyProfileDTO{" +
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
            ", user=" + getUser() +
            "}";
    }
}
