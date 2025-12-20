package com.example.batchProcessing.repo;

import com.example.batchProcessing.entity.HelperEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface HelperEntityRepo extends JpaRepository<HelperEntity, Long> {

}
