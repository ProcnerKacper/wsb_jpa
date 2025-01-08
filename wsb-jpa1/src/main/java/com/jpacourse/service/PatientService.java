package com.jpacourse.service;

import com.jpacourse.dto.PatientTO;
import com.jpacourse.dto.SimpleVisitTO;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface PatientService {

    PatientTO findById(final Long id);

    void deleteById(Long id);

    void addVisitToPatient(Long id, Long doctorId, LocalDateTime visitTime, String description);

    List<PatientTO> findPatientsByLastName(String lastName);

    List<SimpleVisitTO> findPatientVisitsById(Long id);

    List<PatientTO> findPatientsByVisitsCount(Long visitsCount);

    List<PatientTO> findPatientsAfterRegisterDate(LocalDate registerDate);
}
