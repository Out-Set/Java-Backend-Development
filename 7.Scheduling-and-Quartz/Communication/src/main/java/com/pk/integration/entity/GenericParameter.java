package com.pk.integration.entity;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import jakarta.persistence.Cacheable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@DynamicUpdate
@DynamicInsert
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Cacheable
@Table(name ="Generic_Parameter_Config", indexes = { @Index(name = "gp_code_index", columnList = "code") })
public abstract class GenericParameter extends BaseEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4479890964131926063L;
	@Column(updatable = false)
	private String code;
	private String name;
	private String description;
	private String parentCode;
	private Integer sequenceNumber;
	private Boolean defaultFlag;

}
