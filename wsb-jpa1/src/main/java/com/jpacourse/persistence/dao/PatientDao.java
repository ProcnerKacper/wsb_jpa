package com.jpacourse.persistence.dao;

import com.jpacourse.persistence.entity.PatientEntity;

public interface PatientDao extends Dao<PatientEntity, Long>
{

    PatientEntity findPatient(Long id);

    void deletePatient(Long id);
}
