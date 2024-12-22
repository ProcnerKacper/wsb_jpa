package com.jpacourse.service;

import com.jpacourse.dto.PatientTO;

import java.time.LocalDateTime;

public interface PatientService {

    PatientTO findById(final Long id);

    void deleteById(Long id);

    void addVisitToPatient(Long id, Long doctorId, LocalDateTime visitTime, String description);
}
