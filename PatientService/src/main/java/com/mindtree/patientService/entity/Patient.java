package com.mindtree.patientService.entity;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Patient_TB2")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Patient {
//	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	private long id;
	
	@Id
	@GenericGenerator(name="Custom_Patient_ID", strategy = "com.mindtree.patientService.entity.CustomPatientIDGenerator")
	@GeneratedValue(generator = "Custom_Patient_ID")
	private String id;
	
	
	private String name;
	private int age;
	private String visitedDoctorID;
	private Date dateOfVist;

}