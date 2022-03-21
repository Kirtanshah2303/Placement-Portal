package com.mycompany.myapp.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CompanyProfileMapperTest {

    private CompanyProfileMapper companyProfileMapper;

    @BeforeEach
    public void setUp() {
        companyProfileMapper = new CompanyProfileMapperImpl();
    }
}
