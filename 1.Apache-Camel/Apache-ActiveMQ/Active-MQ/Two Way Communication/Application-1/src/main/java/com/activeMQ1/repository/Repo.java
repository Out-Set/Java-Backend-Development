package com.activeMQ1.repository;

import com.activeMQ1.entity.Entity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Repo extends JpaRepository<Entity, Integer> {

}
