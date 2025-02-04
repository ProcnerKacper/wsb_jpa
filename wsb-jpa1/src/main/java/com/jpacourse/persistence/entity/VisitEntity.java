package com.jpacourse.persistence.entity;


import java.time.LocalDateTime;

import javax.persistence.*;

@Entity
@Table(name = "VISIT")
public class VisitEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String description;

	@Column(nullable = false)
	private LocalDateTime time;

	@ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "patient_id") // jednostronna
	private PatientEntity patient;

	@ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "doctor_id") // jednostronna
	private DoctorEntity doctor;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
	@JoinColumn(name = "medical_treatment_id") // jednostronna
	private MedicalTreatmentEntity medicalTreatment;

	public DoctorEntity getDoctor() {
		return doctor;
	}

	public void setDoctor(DoctorEntity doctor) {
		this.doctor = doctor;
	}

	public PatientEntity getPatient() {
		return patient;
	}

	public void setPatient(PatientEntity patient) {
		this.patient = patient;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public LocalDateTime getTime() {
		return time;
	}

	public void setTime(LocalDateTime time) {
		this.time = time;
	}

	public MedicalTreatmentEntity getTreatment() {
		return medicalTreatment;
	}
}
