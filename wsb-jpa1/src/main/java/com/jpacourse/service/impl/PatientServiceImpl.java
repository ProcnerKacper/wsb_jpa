package com.jpacourse.service.impl;

import com.jpacourse.dto.PatientTO;
import com.jpacourse.dto.SimpleVisitTO;
import com.jpacourse.mapper.PatientMapper;
import com.jpacourse.mapper.VisitMapper;
import com.jpacourse.persistence.dao.PatientDao;
import com.jpacourse.persistence.entity.PatientEntity;
import com.jpacourse.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


@Service
@Transactional
public class PatientServiceImpl implements PatientService
{
    private final PatientDao patientDao;

    @Autowired
    public PatientServiceImpl(PatientDao pPatientDao)
    {
        patientDao = pPatientDao;
    }

    @Override
    public PatientTO findById(Long id) {
        final PatientEntity entity = patientDao.findOne(id);
        return PatientMapper.mapToTO(entity);
    }

    @Override
    public void deleteById(Long id) {
        patientDao.deletePatient(id);
    }

    @Override
    public void addVisitToPatient(Long id, Long doctorId, LocalDateTime visitTime, String description)
    {
        patientDao.addVisitToPatient(id, doctorId, visitTime, description);
    }
    @Override
    public List<PatientTO> findPatientsByLastName(String lastName)
    {
        final List<PatientEntity> entity = patientDao.findByLastName(lastName);
        return entity.stream().map(PatientMapper::mapToTO).collect(Collectors.toList());
    }

    @Override
    public List<SimpleVisitTO> findPatientVisitsById(Long id) {
        final PatientEntity entity = patientDao.findOne(id);
        return entity.getVisits().stream().map(VisitMapper::mapTO).collect(Collectors.toList());
    }

    @Override
    public List<PatientTO> findPatientsByVisitsCount(Long visitsCount) {
        final List<PatientEntity> entity = patientDao.findByVisitsCount(visitsCount);
        return entity.stream().map(PatientMapper::mapToTO).collect(Collectors.toList());
    }

    @Override
    public List<PatientTO> findPatientsAfterRegisterDate(LocalDate registerDate) {
        final List<PatientEntity> entity = patientDao.findAfterRegisterDate(registerDate);
        return entity.stream().map(PatientMapper::mapToTO).collect(Collectors.toList());
    }


}
