package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A StudentCompanyStatus.
 */
@Entity
@Table(name = "student_company_status")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class StudentCompanyStatus implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull
    @Column(name = "ctc", nullable = false)
    private String ctc;

    @NotNull
    @Size(max = 600)
    @Column(name = "location", length = 600, nullable = false)
    private String location;

    @NotNull
    @Column(name = "status", nullable = false)
    private String status;

    @ManyToOne
    @JsonIgnoreProperties(value = { "user" }, allowSetters = true)
    @JoinColumn(name = "Student_ID")
    private StudentProfile student;

    @ManyToOne
    @JsonIgnoreProperties(value = { "user" }, allowSetters = true)
    @JoinColumn(name = "Company_ID")
    private CompanyProfile company;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public StudentCompanyStatus id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCtc() {
        return this.ctc;
    }

    public StudentCompanyStatus ctc(String ctc) {
        this.setCtc(ctc);
        return this;
    }

    public void setCtc(String ctc) {
        this.ctc = ctc;
    }

    public String getLocation() {
        return this.location;
    }

    public StudentCompanyStatus location(String location) {
        this.setLocation(location);
        return this;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getStatus() {
        return this.status;
    }

    public StudentCompanyStatus status(String status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public StudentProfile getStudent() {
        return this.student;
    }

    public void setStudent(StudentProfile studentProfile) {
        this.student = studentProfile;
    }

    public StudentCompanyStatus student(StudentProfile studentProfile) {
        this.setStudent(studentProfile);
        return this;
    }

    public CompanyProfile getCompany() {
        return this.company;
    }

    public void setCompany(CompanyProfile companyProfile) {
        this.company = companyProfile;
    }

    public StudentCompanyStatus company(CompanyProfile companyProfile) {
        this.setCompany(companyProfile);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof StudentCompanyStatus)) {
            return false;
        }
        return id != null && id.equals(((StudentCompanyStatus) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "StudentCompanyStatus{" +
            "id=" + getId() +
            ", ctc='" + getCtc() + "'" +
            ", location='" + getLocation() + "'" +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
