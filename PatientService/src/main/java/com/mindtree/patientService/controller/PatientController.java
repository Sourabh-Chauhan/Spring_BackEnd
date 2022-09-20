package com.mindtree.patientService.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mindtree.patientService.entity.Patient;
import com.mindtree.patientService.service.PatientService;

@RestController
@RequestMapping("/patient")
public class PatientController {
	@Autowired
	private PatientService service;

	@GetMapping
	public ResponseEntity<List<Patient>> getAllPatients() {
		return new ResponseEntity<List<Patient>>(service.getAllPatients(), HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<Patient> savePatient(@RequestBody Patient patient) {
		return new ResponseEntity<Patient>(service.savePatient(patient), HttpStatus.CREATED);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Patient> getPatientById(@PathVariable("id") String id) {
		Patient patient = service.getPatientById(id);
		return new ResponseEntity<Patient>(patient, HttpStatus.OK);
	}

	@PutMapping("{id}")
	public ResponseEntity<Patient> updatePatient(@PathVariable("id") String id, @RequestBody Patient Patient) {
		return new ResponseEntity<Patient>(service.updatePatient(Patient, id), HttpStatus.OK);
	}

	@DeleteMapping("{id}")
	public ResponseEntity<Patient> deletesPatient(@PathVariable("id") String patientId) {
		Patient deletingPatient = service.getPatientById(patientId);
		service.deletesPatient(patientId);
		return new ResponseEntity<Patient>(deletingPatient, HttpStatus.OK);
	}

}
