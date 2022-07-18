package com.example.demo.service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.LearningResource;
import com.example.demo.repository.LearningResourceRepository;

@Service
public class LearningResourceService {
	
	   @Autowired
	   LearningResourceRepository repo;

	   public List<LearningResource> getLearningResources(){
            return repo.findAll();
	    }

	   
	    public void saveLearningResources(List<LearningResource> list){
	    	for(LearningResource lr : list) {
	    		if(!repo.existsById(lr.getId())) {
	    		    repo.save(lr);
	    		}
	    	}		
	      }
	    
	   
		public String removeLearningResource(int id){
			if(repo.existsById(id)) {
			    repo.deleteById(id);
			    return "Successfully Deleted";
			}
			return "Invalid Id";
		}


	   
	    public List<Double> profitMargin(){
	        List<LearningResource> list = getLearningResources();
	        List<Double> pmList = list.stream().map(el -> (el.getSellingPrice()-el.getCostPrice())/el.getSellingPrice()).collect(Collectors.toList());
	        return pmList;
	    }
	    
	    
	    public List<LearningResource> sortLearingResourcesByProfitMargin(){
	        List<LearningResource> list = getLearningResources();
	        Collections.sort(list, (lr1, lr2) -> {
	        	Double pm1 = (lr1.getSellingPrice() - lr1.getCostPrice())/lr1.getSellingPrice();
                Double pm2 = (lr2.getSellingPrice() - lr2.getCostPrice())/lr2.getSellingPrice();
	        	return pm1.compareTo(pm2);
	        	
	        });
	        return list;
	    }
}



