package com.mycompany.myapp.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.StudentCompanyStatus} entity.
 */
public class StudentCompanyStatusDTO implements Serializable {

    private Long id;

    @NotNull
    private String ctc;

    @NotNull
    @Size(max = 600)
    private String location;

    @NotNull
    private String status;

    private StudentProfileDTO student;

    private CompanyProfileDTO company;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCtc() {
        return ctc;
    }

    public void setCtc(String ctc) {
        this.ctc = ctc;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public StudentProfileDTO getStudent() {
        return student;
    }

    public void setStudent(StudentProfileDTO student) {
        this.student = student;
    }

    public CompanyProfileDTO getCompany() {
        return company;
    }

    public void setCompany(CompanyProfileDTO company) {
        this.company = company;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof StudentCompanyStatusDTO)) {
            return false;
        }

        StudentCompanyStatusDTO studentCompanyStatusDTO = (StudentCompanyStatusDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, studentCompanyStatusDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "StudentCompanyStatusDTO{" +
            "id=" + getId() +
            ", ctc='" + getCtc() + "'" +
            ", location='" + getLocation() + "'" +
            ", status='" + getStatus() + "'" +
            ", student=" + getStudent() +
            ", company=" + getCompany() +
            "}";
    }
}
