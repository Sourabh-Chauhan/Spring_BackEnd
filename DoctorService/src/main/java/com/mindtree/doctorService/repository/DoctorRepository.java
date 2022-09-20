package com.mindtree.doctorService.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mindtree.doctorService.entity.Doctor;

public interface DoctorRepository extends JpaRepository<Doctor, String> {

}
