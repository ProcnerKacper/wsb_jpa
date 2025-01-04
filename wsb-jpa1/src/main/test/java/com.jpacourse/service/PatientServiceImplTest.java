package com.jpacourse.service;

import com.jpacourse.dto.PatientTO;
import com.jpacourse.persistence.dao.DoctorDao;
import com.jpacourse.service.impl.PatientServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PatientServiceImplTest {

    @Autowired
    private PatientServiceImpl patientService;

    @Autowired
    private DoctorDao doctorDao;


    @Test
    void testFindById_Success() {
        PatientTO actualPatientTO = patientService.findById(1L);
        assertNotNull(actualPatientTO);
        assertEquals(actualPatientTO.getId(), 1L);
        assertEquals(actualPatientTO.getFirstName(), "Maria");
        assertEquals(actualPatientTO.getLastName(), "Kwiatkowska");
    }

    @Test
    void testFindById_NotFound() {
        Long patientId = 99L;

        PatientTO actualPatientTO = patientService.findById(patientId);

        assertNull(actualPatientTO);
    }

    @Test
    public void testDeletePatient_ShouldNotDeleteDoctor() {
        patientService.deleteById(1L);
        assertThat(doctorDao.getOne(1L)).isNotNull();
    }
}