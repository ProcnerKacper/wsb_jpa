package com.jpacourse.mapper;

import com.jpacourse.dto.MedicalTreatmentTO;
import com.jpacourse.persistence.entity.MedicalTreatmentEntity;

public final class MedicalTreatmentMapper
{

    public static MedicalTreatmentTO toTO(MedicalTreatmentEntity entity) {
        if (entity == null) {
            return null;
        }
        MedicalTreatmentTO medicalTreatmentTO = new MedicalTreatmentTO();
        medicalTreatmentTO.setId(entity.getId());
        medicalTreatmentTO.setDescription(entity.getDescription());
        medicalTreatmentTO.setType(entity.getType());
        return medicalTreatmentTO;
    }
}