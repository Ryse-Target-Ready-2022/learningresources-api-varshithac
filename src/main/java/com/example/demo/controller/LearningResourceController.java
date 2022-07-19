package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.LearningResource;
import com.example.demo.service.LearningResourceService;

@RestController
@RequestMapping("/learningresources")
public class LearningResourceController {
	
	@Autowired
	LearningResourceService service;
	
	@GetMapping("/")
	public List<LearningResource> getLearningResources(){
       return service.getLearningResources();
	}

	@PostMapping("/")
	public void saveLearningResources(@RequestBody List<LearningResource> list){
	    service.saveLearningResources(list);
	}
	
    @DeleteMapping("/{id}")
    public void removeLearningResource(@PathVariable int id){
		  service.removeLearningResource(id);
	}

}
