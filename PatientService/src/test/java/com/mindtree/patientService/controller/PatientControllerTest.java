package com.mindtree.patientService.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import java.util.Arrays;
import java.sql.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.mindtree.patientService.entity.Patient;
import com.mindtree.patientService.service.PatientService;

@ExtendWith(SpringExtension.class)
@WebMvcTest(value = PatientController.class)
public class PatientControllerTest {
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private PatientService service;

	List<Patient> mockPatiens = Arrays.asList(
			new Patient("PID_00000", "Sourabh Chauhan", 26, "Doc_00001", Date.valueOf("2015-03-31")),
			new Patient("PID_00001", " Test", 26, "Doc_00001", Date.valueOf("2022-09-15"))

	);

	@Test
	public void getAllDoctors() throws Exception {

		// Given
		Mockito.when(service.getAllPatients()).thenReturn(mockPatiens);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/patient").accept(MediaType.APPLICATION_JSON);

		// If this
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		String data = result.getResponse().getContentAsString();

		JSONArray jsonArr = new JSONArray(data);

		for (int i = 0; i < jsonArr.length(); i++) {
			JSONObject jsonObj = jsonArr.getJSONObject(i);
			//System.out.println(jsonObj);
		}

		// then
		assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
		assertEquals(2, jsonArr.length());
		String expected = "[{\"id\":\"PID_00000\",\"name\":\"Sourabh Chauhan\",\"age\":26,\"visitedDoctorID\":\"Doc_00001\",\"dateOfVist\":\"2015-03-31\"},"
				+ "{\"id\":\"PID_00001\",\"name\":\" Test\",\"age\":26,\"visitedDoctorID\":\"Doc_00001\",\"dateOfVist\":\"2022-09-15\"}]";
		JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
	}

	@Test
	public void savePatient() throws Exception {
		Patient patient = new Patient("PID_00001", " Test", 26, "Doc_00001", Date.valueOf("2022-09-15"));

		String patientAsJson = new ObjectMapper().writeValueAsString(patient);
		String url = "/patient";

		when(service.savePatient(any(Patient.class))).thenReturn(patient);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post(url)
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON).content(patientAsJson);

		MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();



		assertEquals(HttpStatus.CREATED.value(), mvcResult.getResponse().getStatus());

		verify(service, times(1)).savePatient(refEq(patient));

	}
	
	@Test
	public void getDoctorById() throws Exception {
		// Given
		Patient patient = new Patient("PID_00001", " Test", 26, "Doc_00001", Date.valueOf("2022-09-15"));
		Mockito.when(service.getPatientById(patient.getId())).thenReturn(patient);
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.get("/patient/"+patient.getId())
				.accept(MediaType.APPLICATION_JSON);

		// If this
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		// then
		assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
	
		String expected = "{\"id\":\"PID_00001\",\"name\":\" Test\",\"age\":26,\"visitedDoctorID\":\"Doc_00001\",\"dateOfVist\":\"2022-09-15\"}";
		JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
		verify(service, times(1)).getPatientById(anyString());
	}
	

}
