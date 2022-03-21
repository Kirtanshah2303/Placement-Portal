package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.StudentProfile;
import com.mycompany.myapp.service.dto.StudentProfileDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link StudentProfile} and its DTO {@link StudentProfileDTO}.
 */
@Mapper(componentModel = "spring", uses = { UserMapper.class })
public interface StudentProfileMapper extends EntityMapper<StudentProfileDTO, StudentProfile> {
    @Mapping(target = "user", source = "user", qualifiedByName = "login")
    StudentProfileDTO toDto(StudentProfile s);

    @Named("studentID")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "studentID", source = "studentID")
    StudentProfileDTO toDtoStudentID(StudentProfile studentProfile);
}
