package com.mindtree.doctorService.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import java.util.Arrays;
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

import com.mindtree.doctorService.entity.Doctor;
import com.mindtree.doctorService.service.DoctorService;

@ExtendWith(SpringExtension.class)
@WebMvcTest(value = DoctorController.class)
public class DoctorControllerTest {
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private DoctorService service;

	List<Doctor> mockClgs = Arrays.asList(
			new Doctor("Doc_00000", "Hemant Kumar Chauhan", 59, "Male", "Medical Jurist", 1),
			new Doctor("Doc_00001", "PK Saksena", 55, "Male", " Medicines", 0));

	@Test
	public void getAllDoctors() throws Exception {
		// Given
		Mockito.when(service.getAllDoctors()).thenReturn(mockClgs);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/doctor").accept(MediaType.APPLICATION_JSON);

		// If this
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		String data = result.getResponse().getContentAsString();
		// System.out.println(data);
		JSONArray jsonArr = new JSONArray(data);

//		for (int i = 0; i < jsonArr.length(); i++) {
//			JSONObject jsonObj = jsonArr.getJSONObject(i);
//			System.out.println(jsonObj);
//		}

		// then
		assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
		assertEquals(2, jsonArr.length());
		String expected = "[{\"id\":\"Doc_00000\",\"name\":\"Hemant Kumar Chauhan\",\"age\":59,\"gender\":\"Male\",\"specialist\":\"Medical Jurist\",\"numberOfpatientAttened\":1},{\"id\":\"Doc_00001\",\"name\":\"PK Saksena\",\"age\":55,\"gender\":\"Male\",\"specialist\":\" Medicines\",\"numberOfpatientAttened\":0}]";
		
		//(String expectedStr, String actualStr, boolean strict  )
		JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
	}

	@Test
	public void saveDoctor() throws Exception {
		Doctor doctor = new Doctor("Doc_00002", "Test Add", 59, "Female", "Medical", 1);

		String doctorAsJson = new ObjectMapper().writeValueAsString(doctor);
		String url = "/doctor";

		when(service.saveDoctor(any(Doctor.class))).thenReturn(doctor);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post(url).accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON).content(doctorAsJson);

		MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();

		int actualResult = mvcResult.getResponse().getStatus();
		int expectedResult = HttpStatus.CREATED.value();

		assertEquals(expectedResult, actualResult);

		verify(service, times(1)).saveDoctor(refEq(doctor));

	}

	@Test
	public void getDoctorById() throws Exception {
		// Given
		Doctor doctor = new Doctor("Doc_00002", "Test Add", 59, "Female", "Medical", 1);
		Mockito.when(service.getDoctorById(doctor.getId())).thenReturn(doctor);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/doctor/"+doctor.getId())
				.accept(MediaType.APPLICATION_JSON);

		// If this
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		// then
		assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
		String expected = "{\"id\":\"Doc_00002\",\"name\":\"Test Add\",\"age\":59,\"gender\":\"Female\",\"specialist\":\"Medical\",\"numberOfpatientAttened\":1}";
		JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
		verify(service, times(1)).getDoctorById(anyString());
	}

}
