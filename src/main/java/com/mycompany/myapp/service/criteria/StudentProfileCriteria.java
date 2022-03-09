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
import tech.jhipster.service.filter.LocalDateFilter;
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link com.mycompany.myapp.domain.StudentProfile} entity. This class is used
 * in {@link com.mycompany.myapp.web.rest.StudentProfileResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /student-profiles?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
public class StudentProfileCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter studentID;

    private StringFilter personalEmail;

    private StringFilter address;

    private LongFilter contactNumber;

    private StringFilter linkedinProfile;

    private LocalDateFilter dob;

    private StringFilter location;

    private StringFilter githubProfile;

    private DoubleFilter cgpa;

    private IntegerFilter noOfBacklogs;

    private LongFilter userId;

    private Boolean distinct;

    public StudentProfileCriteria() {}

    public StudentProfileCriteria(StudentProfileCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.studentID = other.studentID == null ? null : other.studentID.copy();
        this.personalEmail = other.personalEmail == null ? null : other.personalEmail.copy();
        this.address = other.address == null ? null : other.address.copy();
        this.contactNumber = other.contactNumber == null ? null : other.contactNumber.copy();
        this.linkedinProfile = other.linkedinProfile == null ? null : other.linkedinProfile.copy();
        this.dob = other.dob == null ? null : other.dob.copy();
        this.location = other.location == null ? null : other.location.copy();
        this.githubProfile = other.githubProfile == null ? null : other.githubProfile.copy();
        this.cgpa = other.cgpa == null ? null : other.cgpa.copy();
        this.noOfBacklogs = other.noOfBacklogs == null ? null : other.noOfBacklogs.copy();
        this.userId = other.userId == null ? null : other.userId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public StudentProfileCriteria copy() {
        return new StudentProfileCriteria(this);
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

    public StringFilter getStudentID() {
        return studentID;
    }

    public StringFilter studentID() {
        if (studentID == null) {
            studentID = new StringFilter();
        }
        return studentID;
    }

    public void setStudentID(StringFilter studentID) {
        this.studentID = studentID;
    }

    public StringFilter getPersonalEmail() {
        return personalEmail;
    }

    public StringFilter personalEmail() {
        if (personalEmail == null) {
            personalEmail = new StringFilter();
        }
        return personalEmail;
    }

    public void setPersonalEmail(StringFilter personalEmail) {
        this.personalEmail = personalEmail;
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

    public LocalDateFilter getDob() {
        return dob;
    }

    public LocalDateFilter dob() {
        if (dob == null) {
            dob = new LocalDateFilter();
        }
        return dob;
    }

    public void setDob(LocalDateFilter dob) {
        this.dob = dob;
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

    public StringFilter getGithubProfile() {
        return githubProfile;
    }

    public StringFilter githubProfile() {
        if (githubProfile == null) {
            githubProfile = new StringFilter();
        }
        return githubProfile;
    }

    public void setGithubProfile(StringFilter githubProfile) {
        this.githubProfile = githubProfile;
    }

    public DoubleFilter getCgpa() {
        return cgpa;
    }

    public DoubleFilter cgpa() {
        if (cgpa == null) {
            cgpa = new DoubleFilter();
        }
        return cgpa;
    }

    public void setCgpa(DoubleFilter cgpa) {
        this.cgpa = cgpa;
    }

    public IntegerFilter getNoOfBacklogs() {
        return noOfBacklogs;
    }

    public IntegerFilter noOfBacklogs() {
        if (noOfBacklogs == null) {
            noOfBacklogs = new IntegerFilter();
        }
        return noOfBacklogs;
    }

    public void setNoOfBacklogs(IntegerFilter noOfBacklogs) {
        this.noOfBacklogs = noOfBacklogs;
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
        final StudentProfileCriteria that = (StudentProfileCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(studentID, that.studentID) &&
            Objects.equals(personalEmail, that.personalEmail) &&
            Objects.equals(address, that.address) &&
            Objects.equals(contactNumber, that.contactNumber) &&
            Objects.equals(linkedinProfile, that.linkedinProfile) &&
            Objects.equals(dob, that.dob) &&
            Objects.equals(location, that.location) &&
            Objects.equals(githubProfile, that.githubProfile) &&
            Objects.equals(cgpa, that.cgpa) &&
            Objects.equals(noOfBacklogs, that.noOfBacklogs) &&
            Objects.equals(userId, that.userId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            studentID,
            personalEmail,
            address,
            contactNumber,
            linkedinProfile,
            dob,
            location,
            githubProfile,
            cgpa,
            noOfBacklogs,
            userId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "StudentProfileCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (studentID != null ? "studentID=" + studentID + ", " : "") +
            (personalEmail != null ? "personalEmail=" + personalEmail + ", " : "") +
            (address != null ? "address=" + address + ", " : "") +
            (contactNumber != null ? "contactNumber=" + contactNumber + ", " : "") +
            (linkedinProfile != null ? "linkedinProfile=" + linkedinProfile + ", " : "") +
            (dob != null ? "dob=" + dob + ", " : "") +
            (location != null ? "location=" + location + ", " : "") +
            (githubProfile != null ? "githubProfile=" + githubProfile + ", " : "") +
            (cgpa != null ? "cgpa=" + cgpa + ", " : "") +
            (noOfBacklogs != null ? "noOfBacklogs=" + noOfBacklogs + ", " : "") +
            (userId != null ? "userId=" + userId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
