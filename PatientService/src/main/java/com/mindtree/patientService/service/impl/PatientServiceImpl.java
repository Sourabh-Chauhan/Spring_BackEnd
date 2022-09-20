package com.mindtree.patientService.service.impl;

import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.mindtree.patientService.entity.Patient;
import com.mindtree.patientService.exception.ResourceNotFoundException;
import com.mindtree.patientService.repository.PatientRepository;
import com.mindtree.patientService.service.PatientService;

@Service
public class PatientServiceImpl implements PatientService {

	@Autowired
	private PatientRepository repository;

	@Autowired
	@Lazy
	private RestTemplate template;

	// REST CONTROLLERS

	@Override
	public List<Patient> getAllPatients() {
		return repository.findAll();
	}

	@Override
	public Patient savePatient(Patient patient) {
		template.getForObject("http://DOCTOR-SERVICE/doctor/addPatientToDoctor/"+patient.getVisitedDoctorID(), Object.class);
		return repository.save(patient);
	}

	@Override
	public Patient getPatientById(String id) {
		return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Patient", "Id", id));
	}

	@Override
	public Patient updatePatient(Patient patient, String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String deletesPatient(String patientId) {
		getPatientById(patientId);
		repository.deleteById(patientId);
		return "Collage with id : " + patientId + " is deleted.";

	}

}
