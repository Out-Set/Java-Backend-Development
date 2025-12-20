package com.activeMQ.reqRes.repository;

import com.activeMQ.reqRes.entity.Entity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Repo extends JpaRepository<Entity, Integer> {

}
