package com.mindtree.patientService.service;

import java.util.List;

import com.mindtree.patientService.entity.Patient;

public interface PatientService {

	List<Patient> getAllPatients();

	Patient savePatient(Patient patient);

	Patient getPatientById(String id);

	Patient updatePatient(Patient patient, String id);

	String deletesPatient(String patientId);

}
