package com.mycompany.myapp.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class StudentProfileMapperTest {

    private StudentProfileMapper studentProfileMapper;

    @BeforeEach
    public void setUp() {
        studentProfileMapper = new StudentProfileMapperImpl();
    }
}
