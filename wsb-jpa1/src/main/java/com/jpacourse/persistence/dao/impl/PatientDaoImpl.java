package com.jpacourse.persistence.dao.impl;

import com.jpacourse.persistence.dao.PatientDao;
import com.jpacourse.persistence.entity.DoctorEntity;
import com.jpacourse.persistence.entity.PatientEntity;
import com.jpacourse.persistence.entity.VisitEntity;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public class PatientDaoImpl extends AbstractDao<PatientEntity, Long> implements PatientDao
{
    @Override
    public PatientEntity save(PatientEntity entity) {
        entityManager.merge(entity);
        return entity;
    }

    @Override
    public void deletePatient(Long id) {
        var patient = findOne(id);
        entityManager.remove(patient);
    }
    @Override
    public VisitEntity addVisitToPatient(Long patientId, Long doctorId, LocalDateTime visitDate, String description) {
        PatientEntity patient = entityManager.find(PatientEntity.class, patientId);
        DoctorEntity doctor = entityManager.find(DoctorEntity.class, doctorId);

        if (patient == null || doctor == null) {
            throw new IllegalArgumentException("Pacjent lub doktor nie znaleziony.");
        }

        VisitEntity visit = new VisitEntity();
        visit.setDescription(description);
        visit.setTime(visitDate);
        visit.setPatient(patient);
        visit.setDoctor(doctor);

        patient.getVisits().add(visit);
        entityManager.merge(patient);
        return visit;
    }

}
