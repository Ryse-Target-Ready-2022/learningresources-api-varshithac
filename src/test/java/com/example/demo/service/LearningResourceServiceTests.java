package com.example.demo.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.demo.entity.LearningResource;
import com.example.demo.entity.LearningResourceStatus;
import com.example.demo.repository.LearningResourceRepository;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class LearningResourceServiceTests {
	
	@Mock
	LearningResourceRepository repo;
	
	@InjectMocks
	LearningResourceService service;
	
	
	@Test
	public void testProfitMargin() {
		List<LearningResource> learningResources = new ArrayList<>();
        LearningResource learningResource1 = new LearningResource(1311, "Test Name 1", 100.0, 120.0, LearningResourceStatus.LIVE, LocalDate.now(), LocalDate.now().plusMonths(5), LocalDate.now().plusYears(2));
        LearningResource learningResource2 = new LearningResource(1312, "Test Name 2", 120.0, 180.0, LearningResourceStatus.LIVE, LocalDate.now(), LocalDate.now().plusMonths(6), LocalDate.now().plusYears(3));
        learningResources.add(learningResource1);
        learningResources.add(learningResource2);
        
        List<Double> expectedProfitMargins = learningResources.stream().map(el -> (el.getSellingPrice()-el.getCostPrice())/el.getSellingPrice()).collect(Collectors.toList());
        
        when(repo.findAll()).thenReturn(learningResources);
        
        assertEquals(expectedProfitMargins, service.profitMargin());
         
	}
	
	
	@Test
	public void testSortLearingResourcesByProfitMargin() {
		List<LearningResource> learningResources = new ArrayList<>();
        LearningResource learningResource1 = new LearningResource(1311, "Test Name 1", 100.0, 120.0, LearningResourceStatus.LIVE, LocalDate.now(), LocalDate.now().plusMonths(5), LocalDate.now().plusYears(2));
        LearningResource learningResource2 = new LearningResource(1312, "Test Name 2", 120.0, 180.0, LearningResourceStatus.LIVE, LocalDate.now(), LocalDate.now().plusMonths(6), LocalDate.now().plusYears(3));
        learningResources.add(learningResource1);
        learningResources.add(learningResource2);
        
        List<LearningResource> learningResourcesList = new ArrayList<>();
        for(LearningResource lr : learningResources) {
        	learningResourcesList.add(lr);
        }
        
        Collections.sort(learningResources, (lr1, lr2) -> {
        	Double pm1 = (lr1.getSellingPrice() - lr1.getCostPrice())/lr1.getSellingPrice();
            Double pm2 = (lr2.getSellingPrice() - lr2.getCostPrice())/lr2.getSellingPrice();
        	return pm1.compareTo(pm2);
        	
        });
        
        when(repo.findAll()).thenReturn(learningResourcesList);
        
        List<LearningResource> actualLearningResources = service.sortLearingResourcesByProfitMargin();
        
        assertEquals(learningResources, actualLearningResources);
        
	}
	
	@Test
	public void testSaveLearningResources() {
		List<LearningResource> learningResources = new ArrayList<>();
        LearningResource learningResource1 = new LearningResource(1311, "Test Name 1", 100.0, 120.0, LearningResourceStatus.LIVE, LocalDate.now(), LocalDate.now().plusMonths(5), LocalDate.now().plusYears(2));
        LearningResource learningResource2 = new LearningResource(1312, "Test Name 2", 120.0, 180.0, LearningResourceStatus.LIVE, LocalDate.now(), LocalDate.now().plusMonths(6), LocalDate.now().plusYears(3));
        learningResources.add(learningResource1);
        learningResources.add(learningResource2);
        
        service.saveLearningResources(learningResources);

        verify(repo, times(learningResources.size())).save(any(LearningResource.class));
	}
	
	@Test
	public void testRemoveLearningResource() {
		int learningResourceId = 11;
		
		service.removeLearningResource(learningResourceId);
		
		verify(repo, times(1)).deleteById(learningResourceId);
	}

}
