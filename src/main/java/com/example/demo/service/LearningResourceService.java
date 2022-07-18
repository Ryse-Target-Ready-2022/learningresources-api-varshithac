package com.example.demo.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.example.demo.entity.LearningResource;
import com.example.demo.entity.LearningResourceStatus;



public class LearningResourceService {

	 public List<LearningResource> getLearningResources(){

	        List<LearningResource> list = new ArrayList<>();
	        try {
	            File f = new File("LearningResources.csv");

	            if (f.exists()) {
	                BufferedReader fr = new BufferedReader(new FileReader("LearningResources.csv"));
	                String line = null;
	                while ((line = fr.readLine()) != null) {
	                    String[] fields = line.split(",");

	                    int id = Integer.parseInt(fields[0]);
	                    String name = fields[1];
	                    double cp = Double.parseDouble(fields[2]);
	                    double sp = Double.parseDouble(fields[3]);
	                    LearningResourceStatus status = LearningResourceStatus.valueOf(fields[4]);
	                    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	                    LocalDate cd = LocalDate.parse(fields[5],dtf);
	                    LocalDate pd = LocalDate.parse(fields[6],dtf);
	                    LocalDate rd = LocalDate.parse(fields[7],dtf);

	                    LearningResource lr = new LearningResource(id, name, cp, sp, status, cd, pd, rd);

	                    list.add(lr);
	                }
	                fr.close();
	            }
	        }
	        catch(Exception e){
	            System.out.println(e);
	        }
	        return list;
	    }

	    public void saveLearningResources(List<LearningResource> list){
	        try{
	            PrintWriter pw = new PrintWriter(new FileOutputStream("LearningResources.csv"),true);

	            for(LearningResource lr : list){
	                
	                pw.print(lr.getId());
	                pw.print(",");
	                pw.print(lr.getName());
	                pw.print(",");
	                pw.print(lr.getCostPrice());
	                pw.print(",");
	                pw.print(lr.getSellingPrice());
	                pw.print(",");
	                pw.print(lr.getProductStatus());
	                pw.print(",");
	                pw.print(lr.getCreatedDate());
	                pw.print(",");
	                pw.print(lr.getPublishedDate());
	                pw.print(",");
	                pw.print(lr.getRetiredDate());

	                pw.flush();
	                pw.println();
	               
	            }
	            pw.close();
	        }
	        catch(Exception e){
	            System.out.println(e);
	        }
	       
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