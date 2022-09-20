package com.mindtree.doctorService.service;

import java.util.List;

import com.mindtree.doctorService.entity.Doctor;

public interface DoctorService {

	List<Doctor> getAllDoctors();

	Doctor saveDoctor(Doctor doc);

	Doctor getDoctorById(String id);

	String deletesDoctor(String id);

	Doctor updateDoctor(Doctor doctor, String id);

	Doctor addPatientToDoctor(String docId);

}
