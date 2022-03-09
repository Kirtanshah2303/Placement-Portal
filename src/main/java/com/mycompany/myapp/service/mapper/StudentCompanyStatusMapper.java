package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.StudentCompanyStatus;
import com.mycompany.myapp.service.dto.StudentCompanyStatusDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link StudentCompanyStatus} and its DTO {@link StudentCompanyStatusDTO}.
 */
@Mapper(componentModel = "spring", uses = { StudentProfileMapper.class, CompanyProfileMapper.class })
public interface StudentCompanyStatusMapper extends EntityMapper<StudentCompanyStatusDTO, StudentCompanyStatus> {
    @Mapping(target = "student", source = "student", qualifiedByName = "studentID")
    @Mapping(target = "company", source = "company", qualifiedByName = "id")
    StudentCompanyStatusDTO toDto(StudentCompanyStatus s);
}
