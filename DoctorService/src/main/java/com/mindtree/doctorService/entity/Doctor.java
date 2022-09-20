package com.mindtree.doctorService.entity;

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
@Table(name = "Doctor_TB2")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Doctor {

	@Id
	@GenericGenerator(name="Custom_Doc_ID", strategy = "com.mindtree.doctorService.entity.CustomDoctorIDGenerator")
	@GeneratedValue(generator = "Custom_Doc_ID")
	private String id;
	
//	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	private long id;
	
	private String name;
	private int age;
	private String gender;
	private String specialist;
	private int numberOfpatientAttened;
}
