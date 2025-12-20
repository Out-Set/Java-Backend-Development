package com.savan.quartz.genericparam;

import com.savan.quartz.entity.GenericParameter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GenericParameterRepo extends JpaRepository<GenericParameter, Long> {

}
