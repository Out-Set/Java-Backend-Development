package com.pk.clustercentroid.domainobject;

import jakarta.persistence.Entity;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.pk.integration.entity.GenericParameter;

@Entity
@DynamicUpdate
@DynamicInsert
public class ScaleUpGridProcessType extends GenericParameter {
	private static final long serialVersionUID = 1L;
}
