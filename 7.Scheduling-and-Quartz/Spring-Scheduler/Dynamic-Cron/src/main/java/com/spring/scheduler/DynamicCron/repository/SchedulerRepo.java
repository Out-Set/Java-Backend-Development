package com.spring.scheduler.DynamicCron.repository;

import org.hibernate.event.spi.SaveOrUpdateEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.Query;
import jakarta.persistence.StoredProcedureQuery;
import jakarta.transaction.Transactional;

@Repository
public class SchedulerRepo {
	
	@Autowired
	private EntityManager entityManager;
	
	
	@Transactional
	@Modifying
	public void executeProcedure1() {
		// TODO Auto-generated method stub
		Query query = entityManager.createNativeQuery("call INSERT_CURRENT_TIME()");
		query.executeUpdate();
	}
	
	@Transactional
	@Modifying
	 public void executeProcedure2(int id, String name) {
		StoredProcedureQuery storedProcedure = entityManager.createStoredProcedureQuery("insertuser");

		// Set parameters
		storedProcedure.registerStoredProcedureParameter("param1", Integer.class, ParameterMode.IN);
		storedProcedure.registerStoredProcedureParameter("param2", String.class, ParameterMode.IN);

		storedProcedure.setParameter("param1", id);
		storedProcedure.setParameter("param2", name);

		// Execute the stored procedure
		storedProcedure.execute();
	}
	
	@Transactional
	@Modifying
	 public String executeProcedure3(int arg) {
		StoredProcedureQuery storedProcedure = entityManager.createStoredProcedureQuery("proc_nil_visit");

		// Set parameters
		storedProcedure.registerStoredProcedureParameter("param1", Integer.class, ParameterMode.IN);

		storedProcedure.setParameter("param1", arg);

		// Execute the stored procedure
		String status = "TRUE";
		try{
			storedProcedure.execute();
		} catch (Exception e){
			status = "FALSE";
		}
		System.out.println("Status:: "+status);
		return status;		
	}
}
