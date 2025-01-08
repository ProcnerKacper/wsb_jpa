package com.jpacourse.persistence.dao.impl;

import com.jpacourse.persistence.dao.DoctorDao;
import com.jpacourse.persistence.dao.PatientDao;
import com.jpacourse.persistence.entity.DoctorEntity;
import com.jpacourse.persistence.entity.PatientEntity;
import com.jpacourse.persistence.entity.VisitEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public class PatientDaoImpl extends AbstractDao<PatientEntity, Long> implements PatientDao
{
    @Autowired
    private DoctorDao doctorDao;

    @Override
    public void deletePatient(Long id) {
        var patient = findOne(id);
        delete(patient);
    }
    @Override
    public VisitEntity addVisitToPatient(Long patientId, Long doctorId, LocalDateTime visitDate, String description) {
        PatientEntity patient = findOne(patientId);
        DoctorEntity doctor = doctorDao.findOne(doctorId);

        if (patient == null || doctor == null) {
            throw new IllegalArgumentException("Pacjent lub doktor nie znaleziony.");
        }

        VisitEntity visit = new VisitEntity();
        visit.setDescription(description);
        visit.setTime(visitDate);
        visit.setPatient(patient);
        visit.setDoctor(doctor);

        patient.getVisits().add(visit);
        update(patient);
        return visit;
    }

    @Override
    public List<PatientEntity> findByLastName(String lastName) {
       return entityManager.createQuery("SELECT p FROM PatientEntity p WHERE p.lastName = :lastName",PatientEntity.class)
               .setParameter("lastName",lastName)
               .getResultList();
    }

    @Override
    public List<PatientEntity> findByVisitsCount(Long visitsCount) {
        return entityManager.createQuery("SELECT p FROM PatientEntity p WHERE  (SELECT COUNT(w) FROM VisitEntity w WHERE w.patient.id = p.id) > :visitsCount",PatientEntity.class)
                .setParameter("visitsCount",visitsCount)
                .getResultList();
    }

    @Override
    public List<PatientEntity> findAfterRegisterDate(LocalDate registerDate) {
        return entityManager.createQuery("SELECT p FROM PatientEntity p WHERE dateOfRegistration > :registerDate",PatientEntity.class)
                .setParameter("registerDate",registerDate)
                .getResultList();
    }

}
