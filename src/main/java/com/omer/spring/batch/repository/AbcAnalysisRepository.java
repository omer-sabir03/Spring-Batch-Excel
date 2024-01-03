package com.omer.spring.batch.repository;

import com.omer.spring.batch.entity.AbcAnalysisCloud;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AbcAnalysisRepository extends JpaRepository<AbcAnalysisCloud,String> {
}
