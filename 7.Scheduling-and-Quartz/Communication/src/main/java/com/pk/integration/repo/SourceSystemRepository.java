package com.pk.integration.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pk.integration.entity.SourceSystemIdentifier;

public interface SourceSystemRepository extends JpaRepository<SourceSystemIdentifier, Long> {
}
 