package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.CompanyProfile;
import com.mycompany.myapp.service.dto.CompanyProfileDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link CompanyProfile} and its DTO {@link CompanyProfileDTO}.
 */
@Mapper(componentModel = "spring", uses = { UserMapper.class })
public interface CompanyProfileMapper extends EntityMapper<CompanyProfileDTO, CompanyProfile> {
    @Mapping(target = "user", source = "user", qualifiedByName = "login")
    CompanyProfileDTO toDto(CompanyProfile s);

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CompanyProfileDTO toDtoId(CompanyProfile companyProfile);
}
