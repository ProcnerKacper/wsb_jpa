package com.jpacourse.service;

import com.jpacourse.dto.PatientTO;
import com.jpacourse.dto.SimpleVisitTO;
import com.jpacourse.persistence.dao.DoctorDao;
import com.jpacourse.persistence.dao.PatientDao;
import com.jpacourse.persistence.dao.VisitDao;
import com.jpacourse.rest.exception.EntityNotFoundException;
import com.jpacourse.service.impl.PatientServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PatientServiceImplTest {

    @Autowired
    private PatientServiceImpl patientService;

    @Autowired
    private DoctorDao doctorDao;

    @Autowired
    private PatientDao patientDao;

    @Autowired
    private VisitDao visitDao;


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
    @Transactional
    public void testDeletePatient_ShouldNotDeleteDoctor() {
        final PatientTO patient = patientService.findById(1L);
        List<SimpleVisitTO> visits = patient.getVisits();
        patientService.deleteById(1L);
        assertThrows(JpaObjectRetrievalFailureException.class,  () -> {
            visitDao.getOne(visits.get(0).getId());
        });
        assertThrows(JpaObjectRetrievalFailureException.class,  () -> {
            patientDao.getOne(1L);
        });
        assertThat(doctorDao.getOne(1L)).isNotNull();

    }

    @Test
    public void testGetAllVisitForPatient_Success() {
        List<SimpleVisitTO> visits = patientService.findPatientVisitsById(1L);
        assertThat(visits.size()).isEqualTo(3);
        assertThat(visits.get(0).getId()).isEqualTo(1L);
        assertThat(visits.get(1).getId()).isEqualTo(7L);
        assertThat(visits.get(2).getId()).isEqualTo(8L);
    }
}