package com.savan.quartz.entity;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Getter
@Setter
@Entity
@DynamicUpdate
@DynamicInsert
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Cacheable
@Table(name ="Generic_Parameter_Config", indexes = { @Index(name = "gp_code_index", columnList = "code") })
public abstract class GenericParameter extends BaseEntity {

	private static final long serialVersionUID = 4479890964131926063L;
	@Column(updatable = false)
	private String code;
	private String name;
	private String description;
	private String parentCode;
	private Integer sequenceNumber;
	private Boolean defaultFlag;

}
