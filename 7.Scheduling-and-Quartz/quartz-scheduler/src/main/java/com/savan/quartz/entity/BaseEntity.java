package com.savan.quartz.entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@MappedSuperclass
public abstract class BaseEntity implements Serializable {
	private static final long serialVersionUID = -5364714940059919768L;
	protected static final String UUID_SEPARATOR = "-";
	@Id
	@GenericGenerator(name = "sequencePerEntityGenerator", type = HibernateSequenceGenerator.class, parameters = {
			@Parameter(name = "prefer_sequence_per_entity", value = "true"),
			@Parameter(name = "sequence_per_entity_suffix", value = "_seq"),
			@Parameter(name = "initial_value", value = "5000000") })
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "sequencePerEntityGenerator")
	private Long id;
    private Long tenantId;
	private String createdBy;
	private LocalDateTime creationTimeStamp;
	private String authorizedBy;
	private LocalDateTime authorizationTimeStamp;
	private String lastUpdatedBy;
	private LocalDateTime lastUpdatedTimeStamp;
	private Date makeBusinessDate;
	private Date authorizationBusinessDate;

	public Long getId() {
		return this.id;
	}
}