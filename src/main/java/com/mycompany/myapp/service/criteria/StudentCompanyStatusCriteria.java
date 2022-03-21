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
 * Criteria class for the {@link com.mycompany.myapp.domain.StudentCompanyStatus} entity. This class is used
 * in {@link com.mycompany.myapp.web.rest.StudentCompanyStatusResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /student-company-statuses?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
public class StudentCompanyStatusCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter ctc;

    private StringFilter location;

    private StringFilter status;

    private LongFilter studentId;

    private LongFilter companyId;

    private Boolean distinct;

    public StudentCompanyStatusCriteria() {}

    public StudentCompanyStatusCriteria(StudentCompanyStatusCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.ctc = other.ctc == null ? null : other.ctc.copy();
        this.location = other.location == null ? null : other.location.copy();
        this.status = other.status == null ? null : other.status.copy();
        this.studentId = other.studentId == null ? null : other.studentId.copy();
        this.companyId = other.companyId == null ? null : other.companyId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public StudentCompanyStatusCriteria copy() {
        return new StudentCompanyStatusCriteria(this);
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

    public StringFilter getStatus() {
        return status;
    }

    public StringFilter status() {
        if (status == null) {
            status = new StringFilter();
        }
        return status;
    }

    public void setStatus(StringFilter status) {
        this.status = status;
    }

    public LongFilter getStudentId() {
        return studentId;
    }

    public LongFilter studentId() {
        if (studentId == null) {
            studentId = new LongFilter();
        }
        return studentId;
    }

    public void setStudentId(LongFilter studentId) {
        this.studentId = studentId;
    }

    public LongFilter getCompanyId() {
        return companyId;
    }

    public LongFilter companyId() {
        if (companyId == null) {
            companyId = new LongFilter();
        }
        return companyId;
    }

    public void setCompanyId(LongFilter companyId) {
        this.companyId = companyId;
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
        final StudentCompanyStatusCriteria that = (StudentCompanyStatusCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(ctc, that.ctc) &&
            Objects.equals(location, that.location) &&
            Objects.equals(status, that.status) &&
            Objects.equals(studentId, that.studentId) &&
            Objects.equals(companyId, that.companyId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, ctc, location, status, studentId, companyId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "StudentCompanyStatusCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (ctc != null ? "ctc=" + ctc + ", " : "") +
            (location != null ? "location=" + location + ", " : "") +
            (status != null ? "status=" + status + ", " : "") +
            (studentId != null ? "studentId=" + studentId + ", " : "") +
            (companyId != null ? "companyId=" + companyId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
