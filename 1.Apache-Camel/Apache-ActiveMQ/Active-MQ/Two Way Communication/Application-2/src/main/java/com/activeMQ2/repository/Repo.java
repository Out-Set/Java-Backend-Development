package com.activeMQ2.repository;

import com.activeMQ2.entity.Entity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Repo extends JpaRepository<Entity, Integer> {

}
