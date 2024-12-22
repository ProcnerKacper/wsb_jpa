package com.jpacourse.persistence.dao;

import com.jpacourse.persistence.entity.PatientEntity;
import com.jpacourse.persistence.entity.VisitEntity;

import java.time.LocalDateTime;

public interface PatientDao extends Dao<PatientEntity, Long>
{

    PatientEntity findPatient(Long id);

    void deletePatient(Long id);

    VisitEntity addVisitToPatient(Long patientId, Long doctorId, LocalDateTime visitDate, String description);
}
