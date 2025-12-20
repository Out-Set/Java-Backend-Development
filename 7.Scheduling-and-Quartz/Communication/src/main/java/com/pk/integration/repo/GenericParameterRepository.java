package com.pk.integration.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pk.integration.entity.GenericParameter;

@Repository
public interface GenericParameterRepository<T extends GenericParameter> extends JpaRepository<T, Long>, CustomGenericParameterRepository {
	T findByCodeAndNameIn(String code, List<String> nameTest);
}
