package com.jpacourse.rest;

import com.jpacourse.dto.NewVisitTO;
import com.jpacourse.dto.PatientTO;
import com.jpacourse.dto.SimpleVisitTO;
import com.jpacourse.rest.exception.EntityNotFoundException;
import com.jpacourse.service.PatientService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Null;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
public class PatientController
{

    private final PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }


    @GetMapping("/patient")
    List<PatientTO> adVisitToPatient(@RequestParam String lastName)
    {
        return patientService.findPatientsByLastName(lastName);
    }
    @GetMapping("/patient/visits-count")
    List<PatientTO> getPatientsByVisitCount(@RequestParam Long visitsCount)
    {
        return patientService.findPatientsByVisitsCount(visitsCount);
    }

    @GetMapping("/patient/register-after")
    List<PatientTO> getPatientsAfterRegisterDate(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date)
    {
        return patientService.findPatientsAfterRegisterDate(date);
    }

    @GetMapping("/patient/{id}")
    PatientTO findBaId(@PathVariable final Long id) {
        final PatientTO patient = patientService.findById(id);
        if(patient != null)
        {
            return patient;
        }
        throw new EntityNotFoundException(id);
    }

    @DeleteMapping("/patient/{id}")
    Long deleteById(@PathVariable final Long id) {
        patientService.deleteById(id);
        return id;
    }

    @PostMapping("/patient/{id}/visit")
    String adVisitToPatient(@PathVariable Long id,
                            @RequestBody NewVisitTO newVisit)
    {
        try {
            patientService.addVisitToPatient(id, newVisit.getDoctorId(), newVisit.getVisitDate(), newVisit.getDescription());
            return "Wizyta dodana pomyślnie";
        } catch (Exception e) {
            return "Błąd: " + e.getMessage();
        }
    }

    @GetMapping("/patient/{id}/visits")
    List<SimpleVisitTO> adVisitToPatient(@PathVariable Long id) {
        return patientService.findPatientVisitsById(id);
    }
}
