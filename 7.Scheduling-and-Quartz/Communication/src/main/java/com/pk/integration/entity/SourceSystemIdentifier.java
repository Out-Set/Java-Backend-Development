package com.pk.integration.entity;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import jakarta.persistence.Cacheable;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@DynamicUpdate
@DynamicInsert
@Cacheable
@Getter
@Setter
@Table(name="source_system_identifier_mst2")
public class SourceSystemIdentifier {
	
	private static final long serialVersionUID = 1L;
	@Id
	private Long id;
	private String name;
	private String code;
	private String description;		
}
