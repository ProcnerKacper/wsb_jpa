package com.jpacourse.persistance.dao.impl;

import com.jpacourse.persistence.dao.impl.PatientDaoImpl;
import com.jpacourse.persistence.entity.PatientEntity;
import com.jpacourse.persistence.entity.VisitEntity;
import org.hibernate.OptimisticLockException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

@SpringBootTest
public class PatientDaoTest {

    @Autowired
    private PatientDaoImpl patientDao;

    @Test
    @Transactional
    public void testAddVisitToPatient() {
        LocalDateTime visitTime = LocalDateTime.now();
        String visitDescription = "wizyta kontrolna";

        patientDao.addVisitToPatient(1L, 1L, visitTime, visitDescription);


        List<VisitEntity> visits = patientDao.getOne(1L).getVisits();

        VisitEntity visit = visits.get(visits.size() - 1);


        assertThat(visit).isNotNull();
        assertThat(visit.getId()).isNotNull();
        assertThat(visit.getTime()).isEqualTo(visitTime);
        assertThat(visit.getDescription()).isEqualTo(visitDescription);

        assertThat(visit.getPatient().getId()).isEqualTo(1L);
        assertThat(visit.getDoctor().getId()).isEqualTo(1L);
    }

    @Test
    @Transactional
    public void testMissingDoctorIdAddVisitToPatient() {
        LocalDateTime visitTime = LocalDateTime.now();
        String visitDescription = "wizyta kontrolna";

        assertThrows(InvalidDataAccessApiUsageException.class,  () -> {
            patientDao.addVisitToPatient(1L, 100L, visitTime, visitDescription);
        });
    }

    @Test
    public void testFindPatientByLastName() {
        String lastName = "Kwiatkowska";

        List<PatientEntity> patients = patientDao.findByLastName(lastName);

        assertThat(patients.size()).isEqualTo(1);

        Optional<PatientEntity> fistPatient = patients.stream().findFirst();
        assertThat(fistPatient.map(PatientEntity::getLastName).orElse(null)).isEqualTo(lastName);

    }

    @Test
    public void testNotFindPatientByLastName() {
        String lastName = "Kwiatkowskasa";

        List<PatientEntity> patients = patientDao.findByLastName(lastName);

        assertThat(patients).isEmpty();

    }

    @Test
    @Transactional
    public void testFindPatientsByVisitCount() {
        List<PatientEntity> patients = patientDao.findByVisitsCount(2L);

        assertThat(patients.size()).isEqualTo(2);
        assertThat(patients.get(0).getId()).isEqualTo(1L);
        assertThat(patients.get(1).getId()).isEqualTo(4L);

        patients.forEach(patient -> {
            assertThat(patient.getVisits().size() < 2);
        });
    }

    @Test
    @Transactional
    public void testFindAllPatientsByVisitCount() {
        List<PatientEntity> patients = patientDao.findByVisitsCount(-1L);

        assertThat(patients.size()).isEqualTo(patientDao.findAll().size());
    }

    @Test
    @Transactional
    public void testFindNoPatientsByVisitCount() {
        List<PatientEntity> patients = patientDao.findByVisitsCount(10L);

        assertThat(patients.size()).isEqualTo(0);
    }

    @Test
    @Transactional
    public void testOptimisticLockingThrowsException() throws InterruptedException {
        final ExecutorService executor = Executors.newFixedThreadPool(2);
        CountDownLatch latch = new CountDownLatch(2);
        executor.execute(() -> {
            try {
                PatientEntity patient1 = patientDao.findOne(1L);
                patient1.setFirstName("Updated Name 1");
                patientDao.update(patient1);
            } catch (OptimisticLockException e) {
                System.out.println("Exception in Thread 1: " + e.getMessage());
            } finally {
                latch.countDown();
            }
        });
        executor.execute(() -> {
            try {
                PatientEntity patient2 = patientDao.findOne(1L);
                patient2.setFirstName("Updated Name 2");
                patientDao.update(patient2);
            } catch (OptimisticLockException e) {
                System.out.println("Exception in Thread 2: " + e.getMessage());
            } finally {
                latch.countDown();
            }
        });
        latch.await();
        executor.shutdown();
        assertTrue(executor.awaitTermination(1, TimeUnit.MINUTES));
        assertTrue("The version should have been incremented, indicating an optimistic lock conflict. " + patientDao.getOne(1L).getVersion(),patientDao.getOne(1L).getVersion() > 0);
    }
}