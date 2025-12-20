package com.savan.quartz.scheduler.clustercentroid;

import com.savan.quartz.entity.GenericParameter;
import jakarta.persistence.Entity;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@DynamicUpdate
@DynamicInsert
public class ScaleUpGridProcessType extends GenericParameter {
	private static final long serialVersionUID = 1L;
}
