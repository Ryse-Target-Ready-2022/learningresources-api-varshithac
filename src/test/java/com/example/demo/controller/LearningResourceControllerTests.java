package com.example.demo.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import com.example.demo.entity.LearningResource;
import com.example.demo.entity.LearningResourceStatus;
import com.example.demo.service.LearningResourceService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(LearningResourceController.class)
public class LearningResourceControllerTests {

	@MockBean
	LearningResourceService service;
	
	@Autowired
	ObjectMapper objectMapper;
	
	@Autowired
	MockMvc mockMvc;
	
	@Test
	public void testGetLearningResources() {
		List<LearningResource> learningResources = new ArrayList<>();
        LearningResource learningResource1 = new LearningResource(1311, "Test Name 1", 100.0, 120.0, LearningResourceStatus.LIVE, LocalDate.now(), LocalDate.now().plusMonths(5), LocalDate.now().plusYears(2));
        LearningResource learningResource2 = new LearningResource(1312, "Test Name 2", 120.0, 180.0, LearningResourceStatus.LIVE, LocalDate.now(), LocalDate.now().plusMonths(6), LocalDate.now().plusYears(3));
        learningResources.add(learningResource1);
        learningResources.add(learningResource2);
        
        when(service.getLearningResources()).thenReturn(learningResources);
        
        try {
        String exceptedResponse = objectMapper.writeValueAsString(learningResources);
        
        String actualResponse = mockMvc.perform(MockMvcRequestBuilders.get("/learningresources/"))
        		                       .andExpect(status().isOk())
        		                       .andReturn()
                                       .getResponse()
                                       .getContentAsString();
        assertEquals(exceptedResponse, actualResponse);
        
       
        }
        catch(Exception e) {
        	System.out.println(e);
        }
		
	}
	
	@Test
	public void testSaveLearningResources() {
		List<LearningResource> learningResources = new ArrayList<>();
        LearningResource learningResource1 = new LearningResource(1311, "Test Name 1", 100.0, 120.0, LearningResourceStatus.LIVE, LocalDate.now(), LocalDate.now().plusMonths(5), LocalDate.now().plusYears(2));
        LearningResource learningResource2 = new LearningResource(1312, "Test Name 2", 120.0, 180.0, LearningResourceStatus.LIVE, LocalDate.now(), LocalDate.now().plusMonths(6), LocalDate.now().plusYears(3));
        learningResources.add(learningResource1);
        learningResources.add(learningResource2);
        
        try {
			mockMvc.perform(MockMvcRequestBuilders.post("/learningresources/")
					                              .contentType(MediaType.APPLICATION_JSON)
					                              .content(objectMapper.writeValueAsString(learningResources))).andExpect(status().isOk());
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void testRemoveLearningResource() {
		int id = 11;
		try {
			mockMvc.perform(MockMvcRequestBuilders.delete("/learningresources/"+id)).andExpect(status().isOk());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
