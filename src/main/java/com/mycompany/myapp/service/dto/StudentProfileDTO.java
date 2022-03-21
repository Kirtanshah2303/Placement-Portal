package com.mycompany.myapp.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.StudentProfile} entity.
 */
public class StudentProfileDTO implements Serializable {

    private Long id;

    @NotNull
    private String studentID;

    @NotNull
    @Size(max = 600)
    private String personalEmail;

    @NotNull
    @Size(max = 600)
    private String address;

    @NotNull
    private Long contactNumber;

    @NotNull
    @Size(max = 600)
    private String linkedinProfile;

    @NotNull
    private LocalDate dob;

    @NotNull
    @Size(max = 600)
    private String location;

    @NotNull
    @Size(max = 600)
    private String githubProfile;

    @NotNull
    private Double cgpa;

    @NotNull
    private Integer noOfBacklogs;

    private UserDTO user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStudentID() {
        return studentID;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }

    public String getPersonalEmail() {
        return personalEmail;
    }

    public void setPersonalEmail(String personalEmail) {
        this.personalEmail = personalEmail;
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

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getGithubProfile() {
        return githubProfile;
    }

    public void setGithubProfile(String githubProfile) {
        this.githubProfile = githubProfile;
    }

    public Double getCgpa() {
        return cgpa;
    }

    public void setCgpa(Double cgpa) {
        this.cgpa = cgpa;
    }

    public Integer getNoOfBacklogs() {
        return noOfBacklogs;
    }

    public void setNoOfBacklogs(Integer noOfBacklogs) {
        this.noOfBacklogs = noOfBacklogs;
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
        if (!(o instanceof StudentProfileDTO)) {
            return false;
        }

        StudentProfileDTO studentProfileDTO = (StudentProfileDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, studentProfileDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "StudentProfileDTO{" +
            "id=" + getId() +
            ", studentID='" + getStudentID() + "'" +
            ", personalEmail='" + getPersonalEmail() + "'" +
            ", address='" + getAddress() + "'" +
            ", contactNumber=" + getContactNumber() +
            ", linkedinProfile='" + getLinkedinProfile() + "'" +
            ", dob='" + getDob() + "'" +
            ", location='" + getLocation() + "'" +
            ", githubProfile='" + getGithubProfile() + "'" +
            ", cgpa=" + getCgpa() +
            ", noOfBacklogs=" + getNoOfBacklogs() +
            ", user=" + getUser() +
            "}";
    }
}
