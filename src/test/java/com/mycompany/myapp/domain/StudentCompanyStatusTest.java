package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class StudentCompanyStatusTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(StudentCompanyStatus.class);
        StudentCompanyStatus studentCompanyStatus1 = new StudentCompanyStatus();
        studentCompanyStatus1.setId(1L);
        StudentCompanyStatus studentCompanyStatus2 = new StudentCompanyStatus();
        studentCompanyStatus2.setId(studentCompanyStatus1.getId());
        assertThat(studentCompanyStatus1).isEqualTo(studentCompanyStatus2);
        studentCompanyStatus2.setId(2L);
        assertThat(studentCompanyStatus1).isNotEqualTo(studentCompanyStatus2);
        studentCompanyStatus1.setId(null);
        assertThat(studentCompanyStatus1).isNotEqualTo(studentCompanyStatus2);
    }
}
