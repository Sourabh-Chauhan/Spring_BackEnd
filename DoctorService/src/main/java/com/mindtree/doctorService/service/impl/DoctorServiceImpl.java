package com.mindtree.doctorService.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.mindtree.doctorService.entity.Doctor;
import com.mindtree.doctorService.exception.ResourceNotFoundException;
import com.mindtree.doctorService.repository.DoctorRepository;
import com.mindtree.doctorService.service.DoctorService;

@Service
public class DoctorServiceImpl implements DoctorService {

	// Logger logger = LoggerFactory.getLogger(DoctorServiceImpl.class);

	@Autowired
	private DoctorRepository repository;

	@Autowired
	@Lazy
	private RestTemplate template;

	// REST CONTROLLERS

	@Override
	public List<Doctor> getAllDoctors() {
		return repository.findAll();
	}

	@Override
	public Doctor saveDoctor(Doctor doc) {
		return repository.save(doc);
	}

	@Override
	public Doctor getDoctorById(String id) {
		return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Doctor", "Id", id));
	}

	@Override
	public Doctor updateDoctor(Doctor doctor, String id) {

		Doctor existingdoctor = getDoctorById(id);
		System.out.println("Doctor" + existingdoctor);

		if (!(doctor.getName() == null || doctor.getName().isEmpty()))
			existingdoctor.setName(doctor.getName());

		if (doctor.getAge() > 18)
			existingdoctor.setAge(doctor.getAge());

		if (!(doctor.getGender() == null || doctor.getGender().isEmpty()))
			existingdoctor.setGender(doctor.getGender());

		if (!(doctor.getSpecialist() == null || doctor.getSpecialist().isEmpty()))
			existingdoctor.setSpecialist(doctor.getSpecialist());

//		if (doctor.getNumberOfpatientAttened() >=0)
//			existingdoctor.setNumberOfpatientAttened(doctor.getNumberOfpatientAttened());

		repository.save(existingdoctor);
		return existingdoctor;
	}

	@Override
	public Doctor addPatientToDoctor(String docId) {
		Doctor existingdoctor = getDoctorById(docId);
		existingdoctor.setNumberOfpatientAttened(existingdoctor.getNumberOfpatientAttened() + 1);
		repository.save(existingdoctor);
		return existingdoctor;
	}

	@Override
	public String deletesDoctor(String id) {
		getDoctorById(id);
		repository.deleteById(id);
		return "Doctor with id : " + id + " is deleted.";
	}

}
