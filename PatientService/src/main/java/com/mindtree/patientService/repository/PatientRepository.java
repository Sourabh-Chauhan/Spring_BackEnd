package com.mindtree.patientService.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mindtree.patientService.entity.Patient;

public interface PatientRepository extends JpaRepository<Patient, String> {
	
}