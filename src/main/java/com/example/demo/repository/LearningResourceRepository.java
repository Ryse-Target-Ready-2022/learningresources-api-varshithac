package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.LearningResource;

@Repository
public interface LearningResourceRepository extends JpaRepository<LearningResource, Integer>{

}
