package com.pk.integration.repo;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.pk.integration.entity.GenericParameter;

import jakarta.persistence.metamodel.EntityType;

public class CustomGenericParameterRepositoryImpl extends EntityDaoImpl implements CustomGenericParameterRepository {
	
	@Override
	  public List<String> findAllGenericParameterTypes() {
		   List<String> allGenericParameterTypes = new ArrayList<>();
		   Set<EntityType<?>> allEntityTypes = this.getEntityManager().getMetamodel().getEntities();
			for (EntityType<?> et : allEntityTypes) {
				if (GenericParameter.class.isAssignableFrom(et.getJavaType())) {
						//&& !"GenericParameter".equals(et.getJavaType().getSimpleName())) {
					allGenericParameterTypes.add(et.getJavaType().getName());
				}
			}
		   return allGenericParameterTypes;
		}

}
