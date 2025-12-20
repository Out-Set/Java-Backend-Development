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
@Table(name="system_properties_mst")
public class SystemProperties {
	
	private static final long serialVersionUID = 1L;
	@Id
	private Long id;
	private String key;
	private String value;
	private String description;
	private String propertyGroup;	
}
