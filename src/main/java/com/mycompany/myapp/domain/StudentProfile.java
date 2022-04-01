package com.mycompany.myapp.domain;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A StudentProfile.
 */
@Entity
@Table(name = "student_profile")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class StudentProfile implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull
    @Column(name = "student_id", nullable = false)
    private String studentID;

    @NotNull
    @Size(max = 600)
    @Column(name = "personal_email", length = 600, nullable = false)
    private String personalEmail;

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
    @Column(name = "dob", nullable = false)
    private LocalDate dob;

    @NotNull
    @Size(max = 600)
    @Column(name = "location", length = 600, nullable = false)
    private String location;

    @NotNull
    @Size(max = 600)
    @Column(name = "github_profile", length = 600, nullable = false)
    private String githubProfile;

    @NotNull
    @Column(name = "cgpa", nullable = false)
    private Double cgpa;

    @NotNull
    @Column(name = "no_of_backlogs", nullable = false)
    private Integer noOfBacklogs;

    @ManyToOne
    private User user;

    @OneToMany
    private Set<StudentCompanyStatus> studentCompanyStatusSet;

    public Set<StudentCompanyStatus> getStudentCompanyStatusSet() {
        return studentCompanyStatusSet;
    }

    public void setStudentCompanyStatusSet(Set<StudentCompanyStatus> studentCompanyStatusSet) {
        this.studentCompanyStatusSet = studentCompanyStatusSet;
    }

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public StudentProfile id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStudentID() {
        return this.studentID;
    }

    public StudentProfile studentID(String studentID) {
        this.setStudentID(studentID);
        return this;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }

    public String getPersonalEmail() {
        return this.personalEmail;
    }

    public StudentProfile personalEmail(String personalEmail) {
        this.setPersonalEmail(personalEmail);
        return this;
    }

    public void setPersonalEmail(String personalEmail) {
        this.personalEmail = personalEmail;
    }

    public String getAddress() {
        return this.address;
    }

    public StudentProfile address(String address) {
        this.setAddress(address);
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Long getContactNumber() {
        return this.contactNumber;
    }

    public StudentProfile contactNumber(Long contactNumber) {
        this.setContactNumber(contactNumber);
        return this;
    }

    public void setContactNumber(Long contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getLinkedinProfile() {
        return this.linkedinProfile;
    }

    public StudentProfile linkedinProfile(String linkedinProfile) {
        this.setLinkedinProfile(linkedinProfile);
        return this;
    }

    public void setLinkedinProfile(String linkedinProfile) {
        this.linkedinProfile = linkedinProfile;
    }

    public LocalDate getDob() {
        return this.dob;
    }

    public StudentProfile dob(LocalDate dob) {
        this.setDob(dob);
        return this;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public String getLocation() {
        return this.location;
    }

    public StudentProfile location(String location) {
        this.setLocation(location);
        return this;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getGithubProfile() {
        return this.githubProfile;
    }

    public StudentProfile githubProfile(String githubProfile) {
        this.setGithubProfile(githubProfile);
        return this;
    }

    public void setGithubProfile(String githubProfile) {
        this.githubProfile = githubProfile;
    }

    public Double getCgpa() {
        return this.cgpa;
    }

    public StudentProfile cgpa(Double cgpa) {
        this.setCgpa(cgpa);
        return this;
    }

    public void setCgpa(Double cgpa) {
        this.cgpa = cgpa;
    }

    public Integer getNoOfBacklogs() {
        return this.noOfBacklogs;
    }

    public StudentProfile noOfBacklogs(Integer noOfBacklogs) {
        this.setNoOfBacklogs(noOfBacklogs);
        return this;
    }

    public void setNoOfBacklogs(Integer noOfBacklogs) {
        this.noOfBacklogs = noOfBacklogs;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public StudentProfile user(User user) {
        this.setUser(user);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof StudentProfile)) {
            return false;
        }
        return id != null && id.equals(((StudentProfile) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "StudentProfile{" +
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
            "}";
    }
}
