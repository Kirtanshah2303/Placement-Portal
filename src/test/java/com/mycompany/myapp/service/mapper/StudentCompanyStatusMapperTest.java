package com.mycompany.myapp.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class StudentCompanyStatusMapperTest {

    private StudentCompanyStatusMapper studentCompanyStatusMapper;

    @BeforeEach
    public void setUp() {
        studentCompanyStatusMapper = new StudentCompanyStatusMapperImpl();
    }
}
