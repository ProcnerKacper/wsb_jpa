package com.jpacourse.mapper;

import com.jpacourse.dto.SimpleVisitTO;
import com.jpacourse.persistence.entity.VisitEntity;

public class VisitMapper
{
    public static SimpleVisitTO mapTO(final VisitEntity visitEntity)
    {
        if (visitEntity == null)
        {
            return null;
        }
        final SimpleVisitTO simpleVisitTO = new SimpleVisitTO();
        simpleVisitTO.setId(visitEntity.getId());
        simpleVisitTO.setDoctor(visitEntity.getDoctor());
        simpleVisitTO.setTime(visitEntity.getTime());
        simpleVisitTO.setTreatment(MedicalTreatmentMapper.toTO(visitEntity.getTreatment()));
        return simpleVisitTO;
    }
}
