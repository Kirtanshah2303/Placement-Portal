package com.mycompany.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CompanyProfileDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CompanyProfileDTO.class);
        CompanyProfileDTO companyProfileDTO1 = new CompanyProfileDTO();
        companyProfileDTO1.setId(1L);
        CompanyProfileDTO companyProfileDTO2 = new CompanyProfileDTO();
        assertThat(companyProfileDTO1).isNotEqualTo(companyProfileDTO2);
        companyProfileDTO2.setId(companyProfileDTO1.getId());
        assertThat(companyProfileDTO1).isEqualTo(companyProfileDTO2);
        companyProfileDTO2.setId(2L);
        assertThat(companyProfileDTO1).isNotEqualTo(companyProfileDTO2);
        companyProfileDTO1.setId(null);
        assertThat(companyProfileDTO1).isNotEqualTo(companyProfileDTO2);
    }
}
