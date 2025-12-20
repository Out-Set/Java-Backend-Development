package com.pk.integration.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pk.integration.entity.SystemProperties;

public interface SystemPropertiesRepository extends JpaRepository<SystemProperties, Integer> {
}
 