package com.mycompany.myapp.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.BooleanFilter;
import tech.jhipster.service.filter.DoubleFilter;
import tech.jhipster.service.filter.Filter;
import tech.jhipster.service.filter.FloatFilter;
import tech.jhipster.service.filter.IntegerFilter;
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link com.mycompany.myapp.domain.CompanyProfile} entity. This class is used
 * in {@link com.mycompany.myapp.web.rest.CompanyProfileResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /company-profiles?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
public class CompanyProfileCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter email;

    private StringFilter address;

    private LongFilter contactNumber;

    private StringFilter linkedinProfile;

    private StringFilter location;

    private StringFilter technology;

    private StringFilter ctc;

    private StringFilter overview;

    private IntegerFilter bond;

    private StringFilter minimumCriteria;

    private StringFilter type;

    private IntegerFilter noOfOpenings;

    private LongFilter userId;

    private Boolean distinct;

    public CompanyProfileCriteria() {}

    public CompanyProfileCriteria(CompanyProfileCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.email = other.email == null ? null : other.email.copy();
        this.address = other.address == null ? null : other.address.copy();
        this.contactNumber = other.contactNumber == null ? null : other.contactNumber.copy();
        this.linkedinProfile = other.linkedinProfile == null ? null : other.linkedinProfile.copy();
        this.location = other.location == null ? null : other.location.copy();
        this.technology = other.technology == null ? null : other.technology.copy();
        this.ctc = other.ctc == null ? null : other.ctc.copy();
        this.overview = other.overview == null ? null : other.overview.copy();
        this.bond = other.bond == null ? null : other.bond.copy();
        this.minimumCriteria = other.minimumCriteria == null ? null : other.minimumCriteria.copy();
        this.type = other.type == null ? null : other.type.copy();
        this.noOfOpenings = other.noOfOpenings == null ? null : other.noOfOpenings.copy();
        this.userId = other.userId == null ? null : other.userId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public CompanyProfileCriteria copy() {
        return new CompanyProfileCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public LongFilter id() {
        if (id == null) {
            id = new LongFilter();
        }
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getEmail() {
        return email;
    }

    public StringFilter email() {
        if (email == null) {
            email = new StringFilter();
        }
        return email;
    }

    public void setEmail(StringFilter email) {
        this.email = email;
    }

    public StringFilter getAddress() {
        return address;
    }

    public StringFilter address() {
        if (address == null) {
            address = new StringFilter();
        }
        return address;
    }

    public void setAddress(StringFilter address) {
        this.address = address;
    }

    public LongFilter getContactNumber() {
        return contactNumber;
    }

    public LongFilter contactNumber() {
        if (contactNumber == null) {
            contactNumber = new LongFilter();
        }
        return contactNumber;
    }

    public void setContactNumber(LongFilter contactNumber) {
        this.contactNumber = contactNumber;
    }

    public StringFilter getLinkedinProfile() {
        return linkedinProfile;
    }

    public StringFilter linkedinProfile() {
        if (linkedinProfile == null) {
            linkedinProfile = new StringFilter();
        }
        return linkedinProfile;
    }

    public void setLinkedinProfile(StringFilter linkedinProfile) {
        this.linkedinProfile = linkedinProfile;
    }

    public StringFilter getLocation() {
        return location;
    }

    public StringFilter location() {
        if (location == null) {
            location = new StringFilter();
        }
        return location;
    }

    public void setLocation(StringFilter location) {
        this.location = location;
    }

    public StringFilter getTechnology() {
        return technology;
    }

    public StringFilter technology() {
        if (technology == null) {
            technology = new StringFilter();
        }
        return technology;
    }

    public void setTechnology(StringFilter technology) {
        this.technology = technology;
    }

    public StringFilter getCtc() {
        return ctc;
    }

    public StringFilter ctc() {
        if (ctc == null) {
            ctc = new StringFilter();
        }
        return ctc;
    }

    public void setCtc(StringFilter ctc) {
        this.ctc = ctc;
    }

    public StringFilter getOverview() {
        return overview;
    }

    public StringFilter overview() {
        if (overview == null) {
            overview = new StringFilter();
        }
        return overview;
    }

    public void setOverview(StringFilter overview) {
        this.overview = overview;
    }

    public IntegerFilter getBond() {
        return bond;
    }

    public IntegerFilter bond() {
        if (bond == null) {
            bond = new IntegerFilter();
        }
        return bond;
    }

    public void setBond(IntegerFilter bond) {
        this.bond = bond;
    }

    public StringFilter getMinimumCriteria() {
        return minimumCriteria;
    }

    public StringFilter minimumCriteria() {
        if (minimumCriteria == null) {
            minimumCriteria = new StringFilter();
        }
        return minimumCriteria;
    }

    public void setMinimumCriteria(StringFilter minimumCriteria) {
        this.minimumCriteria = minimumCriteria;
    }

    public StringFilter getType() {
        return type;
    }

    public StringFilter type() {
        if (type == null) {
            type = new StringFilter();
        }
        return type;
    }

    public void setType(StringFilter type) {
        this.type = type;
    }

    public IntegerFilter getNoOfOpenings() {
        return noOfOpenings;
    }

    public IntegerFilter noOfOpenings() {
        if (noOfOpenings == null) {
            noOfOpenings = new IntegerFilter();
        }
        return noOfOpenings;
    }

    public void setNoOfOpenings(IntegerFilter noOfOpenings) {
        this.noOfOpenings = noOfOpenings;
    }

    public LongFilter getUserId() {
        return userId;
    }

    public LongFilter userId() {
        if (userId == null) {
            userId = new LongFilter();
        }
        return userId;
    }

    public void setUserId(LongFilter userId) {
        this.userId = userId;
    }

    public Boolean getDistinct() {
        return distinct;
    }

    public void setDistinct(Boolean distinct) {
        this.distinct = distinct;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final CompanyProfileCriteria that = (CompanyProfileCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(email, that.email) &&
            Objects.equals(address, that.address) &&
            Objects.equals(contactNumber, that.contactNumber) &&
            Objects.equals(linkedinProfile, that.linkedinProfile) &&
            Objects.equals(location, that.location) &&
            Objects.equals(technology, that.technology) &&
            Objects.equals(ctc, that.ctc) &&
            Objects.equals(overview, that.overview) &&
            Objects.equals(bond, that.bond) &&
            Objects.equals(minimumCriteria, that.minimumCriteria) &&
            Objects.equals(type, that.type) &&
            Objects.equals(noOfOpenings, that.noOfOpenings) &&
            Objects.equals(userId, that.userId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            email,
            address,
            contactNumber,
            linkedinProfile,
            location,
            technology,
            ctc,
            overview,
            bond,
            minimumCriteria,
            type,
            noOfOpenings,
            userId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CompanyProfileCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (email != null ? "email=" + email + ", " : "") +
            (address != null ? "address=" + address + ", " : "") +
            (contactNumber != null ? "contactNumber=" + contactNumber + ", " : "") +
            (linkedinProfile != null ? "linkedinProfile=" + linkedinProfile + ", " : "") +
            (location != null ? "location=" + location + ", " : "") +
            (technology != null ? "technology=" + technology + ", " : "") +
            (ctc != null ? "ctc=" + ctc + ", " : "") +
            (overview != null ? "overview=" + overview + ", " : "") +
            (bond != null ? "bond=" + bond + ", " : "") +
            (minimumCriteria != null ? "minimumCriteria=" + minimumCriteria + ", " : "") +
            (type != null ? "type=" + type + ", " : "") +
            (noOfOpenings != null ? "noOfOpenings=" + noOfOpenings + ", " : "") +
            (userId != null ? "userId=" + userId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
