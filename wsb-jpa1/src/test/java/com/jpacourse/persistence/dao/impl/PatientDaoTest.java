package com.jpacourse.persistence.dao.impl;

import com.jpacourse.persistence.dao.PatientDao;
import com.jpacourse.persistence.entity.PatientEntity;
import com.jpacourse.persistence.entity.VisitEntity;
import com.jpacourse.persistence.entity.DoctorEntity;
import com.jpacourse.persistence.enums.Specialization;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class PatientDaoTest {

    @Autowired
    private PatientDao patientDao;

    @Autowired
    private EntityManager entityManager;

    private PatientEntity testPatient;
    private DoctorEntity testDoctor;

    @BeforeEach
    public void setUp() {
        testPatient = new PatientEntity();
        testPatient.setFirstName("John");
        testPatient.setLastName("Doe");
        testPatient.setEmail("john.doe@example.com");
        testPatient.setPatientNumber("12345");
        testPatient.setTelephoneNumber("555-555-555");
        testPatient.setDateOfBirth(java.time.LocalDate.of(1980, 1, 1));
        testPatient.setDateOfRegistration(java.time.LocalDate.now());

        // Przygotowanie doktora do testów
        testDoctor = new DoctorEntity();
        testDoctor.setFirstName("Dr. John");
        testDoctor.setLastName("Smith");
        testDoctor.setEmail("dr.john.smith@example.com");
        testDoctor.setDoctorNumber("D001");
        testDoctor.setSpecialization(Specialization.KARDIOLOGIA);  // Zakładając, że masz enum Specialization

        // Zapisanie pacjenta i doktora do bazy danych
        patientDao.save(testPatient);
        entityManager.persist(testDoctor);
    }

    @Test
    @Transactional
    public void testAddVisitToPatient() {
        LocalDateTime visitTime = LocalDateTime.now();
        String visitDescription = "Routine check-up";

        VisitEntity visit = patientDao.addVisitToPatient(testPatient.getId(), testDoctor.getId(), visitTime, visitDescription);

        // Sprawdzamy, czy wizyta została dodana
        assertThat(visit).isNotNull();
        assertThat(visit.getTime()).isEqualTo(visitTime);
        assertThat(visit.getDescription()).isEqualTo(visitDescription);
        assertThat(visit.getPatient()).isEqualTo(testPatient);
        assertThat(visit.getDoctor()).isEqualTo(testDoctor);

        // Sprawdzamy, czy pacjent ma przypisaną wizytę
        assertThat(testPatient.getVisits()).contains(visit);
    }
}