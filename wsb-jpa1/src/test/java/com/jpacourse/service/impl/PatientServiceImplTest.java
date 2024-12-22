package com.jpacourse.service.impl;

import com.jpacourse.dto.PatientTO;
import com.jpacourse.dto.SimpleVisitTO;
import com.jpacourse.persistence.dao.PatientDao;
import com.jpacourse.persistence.entity.DoctorEntity;
import com.jpacourse.persistence.entity.PatientEntity;
import com.jpacourse.persistence.entity.VisitEntity;
import com.jpacourse.persistence.enums.Specialization;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class PatientServiceImplTest {

    @MockBean
    private EntityManager entityManager;

    @MockBean
    private PatientDao patientDao;

    @Autowired
    private PatientServiceImpl patientService;


    @Test
    void testFindById_Success() {
        Long patientId = 1L;

        PatientEntity patientEntity = new PatientEntity();
        patientEntity.setId(patientId);
        patientEntity.setFirstName("John");
        patientEntity.setLastName("Doe");

        List<VisitEntity> visits = new ArrayList<>();
        VisitEntity visit1 = new VisitEntity();
        visit1.setId(1L);
        visit1.setTime(LocalDateTime.of(2023, 1, 10, 10, 0));
        VisitEntity visit2 = new VisitEntity();
        visit2.setId(2L);
        visit2.setTime(LocalDateTime.of(2023, 2, 15, 10, 0));
        visits.add(visit1);
        visits.add(visit2);

        patientEntity.setVisits(visits);

        List<SimpleVisitTO> visitsto = new ArrayList<>();
        SimpleVisitTO visitto1 = new SimpleVisitTO();
        visitto1.setId(1L);
        visitto1.setTime(LocalDateTime.of(2023, 1, 10, 10, 0));
        SimpleVisitTO visitto2 = new SimpleVisitTO();
        visitto2.setId(2L);
        visitto2.setTime(LocalDateTime.of(2023, 2, 15, 10, 0));
        visitsto.add(visitto1);
        visitsto.add(visitto2);

        PatientTO expectedPatientTO = new PatientTO();
        expectedPatientTO.setId(patientId);
        expectedPatientTO.setFirstName("John");
        expectedPatientTO.setLastName("Doe");
        expectedPatientTO.setVisits(visitsto);
        expectedPatientTO.setId(patientId);
        expectedPatientTO.setFirstName("John");
        expectedPatientTO.setLastName("Doe");

        when(patientDao.findPatient(patientId)).thenReturn(patientEntity);

        PatientTO actualPatientTO = patientService.findById(patientId);

        assertNotNull(actualPatientTO);
        assertEquals(expectedPatientTO.getId(), actualPatientTO.getId());
        assertEquals(expectedPatientTO.getFirstName(), actualPatientTO.getFirstName());
        assertEquals(expectedPatientTO.getLastName(), actualPatientTO.getLastName());

        verify(patientDao, times(1)).findPatient(patientId);
        verifyNoMoreInteractions(patientDao);
    }

    @Test
    void testFindById_NotFound() {
        Long patientId = 99L;

        when(patientDao.findPatient(patientId)).thenReturn(null);

        PatientTO actualPatientTO = patientService.findById(patientId);

        assertNull(actualPatientTO);

        verify(patientDao, times(1)).findPatient(patientId);
        verifyNoMoreInteractions(patientDao);
    }

    @Test
    public void testDeletePatient_ShouldNotDeleteDoctor() {
        Long patientId = 1L;
        Long doctorId = 1L;

        DoctorEntity doctor = new DoctorEntity();
        doctor.setId(doctorId);
        doctor.setFirstName("Jan");
        doctor.setLastName("Kowalski");
        doctor.setTelephoneNumber("123456789");
        doctor.setEmail("jan.kowalski@example.com");
        doctor.setDoctorNumber("DOC001");
        doctor.setSpecialization(Specialization.KARDIOLOGIA);

        VisitEntity visit = new VisitEntity();
        visit.setId(1L);
        visit.setDescription("Routine check-up");
        visit.setTime(LocalDateTime.now());
        visit.setDoctor(doctor);

        PatientEntity patient = new PatientEntity();
        patient.setId(patientId);
        patient.setFirstName("Anna");
        patient.setLastName("Nowak");
        patient.setTelephoneNumber("987654321");
        patient.setEmail("anna.nowak@example.com");
        patient.setPatientNumber("PAT001");
        patient.setDateOfBirth(LocalDate.of(1990, 1, 1));
        patient.setDateOfRegistration(LocalDate.of(2020, 1, 1));
        patient.setVisits(Collections.singletonList(visit));

        when(patientDao.findPatient(patientId)).thenReturn(patient);


        patientService.deleteById(patientId);


        verify(patientDao).deletePatient(patientId);


        verify(entityManager, never()).remove(doctor);

    }
}