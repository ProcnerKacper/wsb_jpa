package com.jpacourse.dto;


import com.jpacourse.persistence.entity.DoctorEntity;
import com.jpacourse.persistence.enums.TreatmentType;

import java.io.Serializable;
import java.time.LocalDateTime;

public class SimpleVisitTO implements Serializable
{
    private Long id;

    private LocalDateTime time;

    private DoctorEntity doctor;

    private MedicalTreatmentTO treatment;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public void setDoctor(DoctorEntity doctor) {
        this.doctor = doctor;
    }

    public String getDoctorLastName() {
        return doctor.getLastName();
    }

    public String getDoctorFirstName() {
        return doctor.getFirstName();
    }

    public TreatmentType getTreatmentType() {
        return treatment.getType();
    }

    public void setTreatment(MedicalTreatmentTO treatment) {
        this.treatment = treatment;
    }


}
