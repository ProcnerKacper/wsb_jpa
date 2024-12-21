package com.jpacourse.persistence.dao.impl;

import com.jpacourse.persistence.dao.PatientDao;
import com.jpacourse.persistence.entity.PatientEntity;
import org.springframework.stereotype.Repository;

@Repository
public class PatientDaoImpl extends AbstractDao<PatientEntity, Long> implements PatientDao
{

    @Override
    public PatientEntity findPatient(Long id) {
        return entityManager.find(PatientEntity.class, id);
    }

    @Override
    public void deletePatient(Long id) {
        var patient = findPatient(id);
        entityManager.remove(patient);
    }
}
