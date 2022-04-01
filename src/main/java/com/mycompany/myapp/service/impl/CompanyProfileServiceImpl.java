package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.CompanyProfile;
import com.mycompany.myapp.repository.CompanyProfileRepository;
import com.mycompany.myapp.service.CompanyProfileService;
import com.mycompany.myapp.service.UserService;
import com.mycompany.myapp.service.dto.CompanyProfileDTO;
import com.mycompany.myapp.service.mapper.CompanyProfileMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link CompanyProfile}.
 */
@Service
@Transactional
public class CompanyProfileServiceImpl implements CompanyProfileService {

    private final Logger log = LoggerFactory.getLogger(CompanyProfileServiceImpl.class);

    private final CompanyProfileRepository companyProfileRepository;

    private final CompanyProfileMapper companyProfileMapper;
    private final UserService userService;

    public CompanyProfileServiceImpl(
        CompanyProfileRepository companyProfileRepository,
        CompanyProfileMapper companyProfileMapper,
        UserService userService
    ) {
        this.companyProfileRepository = companyProfileRepository;
        this.companyProfileMapper = companyProfileMapper;
        this.userService = userService;
    }

    @Override
    public CompanyProfileDTO save(CompanyProfileDTO companyProfileDTO) {
        log.debug("Request to save CompanyProfile : {}", companyProfileDTO);
        CompanyProfile companyProfile = companyProfileMapper.toEntity(companyProfileDTO);
        if (companyProfileDTO.getUser() == null) {
            companyProfile.setUser(userService.getUserWithAuthorities().get());
            companyProfile = companyProfileRepository.save(companyProfile);
        } else {
            companyProfile = companyProfileRepository.save(companyProfile);
        }
        //        companyProfile = companyProfileRepository.save(companyProfile);
        return companyProfileMapper.toDto(companyProfile);
    }

    @Override
    public Optional<CompanyProfileDTO> partialUpdate(CompanyProfileDTO companyProfileDTO) {
        log.debug("Request to partially update CompanyProfile : {}", companyProfileDTO);

        return companyProfileRepository
            .findById(companyProfileDTO.getId())
            .map(existingCompanyProfile -> {
                companyProfileMapper.partialUpdate(existingCompanyProfile, companyProfileDTO);

                return existingCompanyProfile;
            })
            .map(companyProfileRepository::save)
            .map(companyProfileMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CompanyProfileDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CompanyProfiles");
        return companyProfileRepository.findAll(pageable).map(companyProfileMapper::toDto);
    }

    public Page<CompanyProfileDTO> findAllWithEagerRelationships(Pageable pageable) {
        return companyProfileRepository.findAllWithEagerRelationships(pageable).map(companyProfileMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CompanyProfileDTO> findOne(Long id) {
        log.debug("Request to get CompanyProfile : {}", id);
        return companyProfileRepository.findOneWithEagerRelationships(id).map(companyProfileMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete CompanyProfile : {}", id);
        companyProfileRepository.deleteById(id);
    }
}
