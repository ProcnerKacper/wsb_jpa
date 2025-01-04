package com.jpacourse.persistance.dao.impl;

import com.jpacourse.persistence.dao.impl.PatientDaoImpl;
import com.jpacourse.persistence.entity.PatientEntity;
import com.jpacourse.persistence.entity.VisitEntity;
import com.jpacourse.persistence.entity.DoctorEntity;
import com.jpacourse.persistence.enums.Specialization;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class PatientDaoTest {

    @Autowired
    private PatientDaoImpl patientDao;

    @Autowired
    private EntityManager entityManager;

    @Test
    @Transactional
    public void testAddVisitToPatient() {
        LocalDateTime visitTime = LocalDateTime.now();
        String visitDescription = "wizyta kontrolna";

        PatientEntity testPatient = new PatientEntity();
        testPatient.setId(1L);
        testPatient.setFirstName("John");
        testPatient.setLastName("Doe");
        testPatient.setEmail("john.doe@example.com");
        testPatient.setPatientNumber("12345");
        testPatient.setTelephoneNumber("555-555-555");
        testPatient.setDateOfBirth(java.time.LocalDate.of(1980, 1, 1));
        testPatient.setDateOfRegistration(java.time.LocalDate.now());


        List<VisitEntity> visits = new ArrayList<>();
        VisitEntity visit1 = new VisitEntity();
        visit1.setId(1L);
        visit1.setTime(LocalDateTime.of(2023, 1, 10, 10, 0));
        VisitEntity visit2 = new VisitEntity();
        visit2.setId(2L);
        visit2.setTime(LocalDateTime.of(2023, 2, 15, 10, 0));
        visits.add(visit1);
        visits.add(visit2);

        testPatient.setVisits(visits);

        DoctorEntity testDoctor = new DoctorEntity();
        testDoctor.setId(1L);
        testDoctor.setFirstName("Dr. John");
        testDoctor.setLastName("Smith");
        testDoctor.setEmail("dr.john.smith@example.com");
        testDoctor.setDoctorNumber("D001");
        testDoctor.setTelephoneNumber("454-444-444");
        testDoctor.setSpecialization(Specialization.KARDIOLOGIA);

        PatientEntity savedPatient = patientDao.save(testPatient);
        DoctorEntity savedDoctor = entityManager.merge(testDoctor);

        VisitEntity visit = patientDao.addVisitToPatient(savedPatient.getId(), savedDoctor.getId(), visitTime, visitDescription);
        
        assertThat(visit).isNotNull();
        assertThat(visit.getTime()).isEqualTo(visitTime);
        assertThat(visit.getDescription()).isEqualTo(visitDescription);
        assertThat(visit.getPatient().getId()).isEqualTo(savedPatient.getId());
        assertThat(visit.getDoctor().getId()).isEqualTo(savedDoctor.getId());
    }
}