package com.jpacourse.rest;

import com.jpacourse.dto.NewVisitTO;
import com.jpacourse.dto.PatientTO;
import com.jpacourse.rest.exception.EntityNotFoundException;
import com.jpacourse.service.PatientService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
public class PatientController
{

    private final PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
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
    String adVisitToPacient(@PathVariable Long id,
                            @RequestBody NewVisitTO newVisit)
    {
        try {
            patientService.addVisitToPatient(id, newVisit.getDoctorId(), newVisit.getVisitDate(), newVisit.getDescription());
            return "Wizyta dodana pomyślnie";
        } catch (Exception e) {
            return "Błąd: " + e.getMessage();
        }
    }
}
