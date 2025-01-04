package com.jpacourse.persistance.dao.impl;

import com.jpacourse.persistence.dao.impl.PatientDaoImpl;
import com.jpacourse.persistence.entity.VisitEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class PatientDaoTest {

    @Autowired
    private PatientDaoImpl patientDao;

    @Test
    @Transactional
    public void testAddVisitToPatient() {
        LocalDateTime visitTime = LocalDateTime.now();
        String visitDescription = "wizyta kontrolna";

        VisitEntity visit = patientDao.addVisitToPatient(1L, 1L, visitTime, visitDescription);
        
        assertThat(visit).isNotNull();
        assertThat(visit.getTime()).isEqualTo(visitTime);
        assertThat(visit.getDescription()).isEqualTo(visitDescription);
        assertThat(visit.getPatient().getId()).isEqualTo(1L);
        assertThat(visit.getDoctor().getId()).isEqualTo(1L);
    }
}