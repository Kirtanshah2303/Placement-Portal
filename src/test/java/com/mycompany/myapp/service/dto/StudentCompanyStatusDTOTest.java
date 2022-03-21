package com.mycompany.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class StudentCompanyStatusDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(StudentCompanyStatusDTO.class);
        StudentCompanyStatusDTO studentCompanyStatusDTO1 = new StudentCompanyStatusDTO();
        studentCompanyStatusDTO1.setId(1L);
        StudentCompanyStatusDTO studentCompanyStatusDTO2 = new StudentCompanyStatusDTO();
        assertThat(studentCompanyStatusDTO1).isNotEqualTo(studentCompanyStatusDTO2);
        studentCompanyStatusDTO2.setId(studentCompanyStatusDTO1.getId());
        assertThat(studentCompanyStatusDTO1).isEqualTo(studentCompanyStatusDTO2);
        studentCompanyStatusDTO2.setId(2L);
        assertThat(studentCompanyStatusDTO1).isNotEqualTo(studentCompanyStatusDTO2);
        studentCompanyStatusDTO1.setId(null);
        assertThat(studentCompanyStatusDTO1).isNotEqualTo(studentCompanyStatusDTO2);
    }
}
