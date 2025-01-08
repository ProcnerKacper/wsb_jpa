package com.jpacourse.persistence.dao;

import com.jpacourse.persistence.entity.PatientEntity;
import com.jpacourse.persistence.entity.VisitEntity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface PatientDao extends Dao<PatientEntity, Long>
{

    void deletePatient(Long id);

    VisitEntity addVisitToPatient(Long patientId, Long doctorId, LocalDateTime visitDate, String description);

    List<PatientEntity> findByLastName(String lastName);

    List<PatientEntity> findByVisitsCount(Long visitsCount);

    List<PatientEntity> findAfterRegisterDate(LocalDate registerDate);
}
