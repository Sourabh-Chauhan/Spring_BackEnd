package com.mindtree.doctorService.controller;

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

import com.mindtree.doctorService.entity.Doctor;
import com.mindtree.doctorService.service.DoctorService;

@RestController
@RequestMapping("/doctor")
public class DoctorController {
	@Autowired
	private DoctorService service;

	@GetMapping
	public ResponseEntity<List<Doctor>> getAllDoctors() {
		return new ResponseEntity<List<Doctor>>(service.getAllDoctors(), HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<Doctor> saveDoctor(@RequestBody Doctor doc) {
		return new ResponseEntity<Doctor>(service.saveDoctor(doc), HttpStatus.CREATED);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Doctor> getDoctorById(@PathVariable("id") String id) {
		Doctor doctor = service.getDoctorById(id);
		return new ResponseEntity<Doctor>(doctor, HttpStatus.OK);
	}

	@PutMapping("{id}")
	public ResponseEntity<Doctor> updateDoctor(@PathVariable("id") String id, @RequestBody Doctor doctor) {
		return new ResponseEntity<Doctor>(service.updateDoctor(doctor, id), HttpStatus.OK);
	}

	@DeleteMapping("{id}")
	public ResponseEntity<Doctor> deletesDoctor(@PathVariable("id") String docId) {
		Doctor doctor = service.getDoctorById(docId);
		service.deletesDoctor(docId);
		return new ResponseEntity<Doctor>(doctor, HttpStatus.OK);
	}

	@GetMapping("addPatientToDoctor/{id}")
	public ResponseEntity<Doctor> addPatientToDoctor(@PathVariable("id") String docId) {
		return new ResponseEntity<Doctor>(service.addPatientToDoctor(docId), HttpStatus.OK);
	}
}
