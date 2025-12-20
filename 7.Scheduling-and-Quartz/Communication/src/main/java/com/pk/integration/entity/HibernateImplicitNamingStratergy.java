package com.pk.integration.entity;

import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.boot.model.naming.ImplicitCollectionTableNameSource;
import org.hibernate.boot.model.naming.ImplicitJoinColumnNameSource;
import org.hibernate.boot.model.naming.ImplicitJoinTableNameSource;
import org.hibernate.boot.model.naming.ImplicitNamingStrategyJpaCompliantImpl;
import org.hibernate.boot.model.naming.ImplicitJoinColumnNameSource.Nature;

public class HibernateImplicitNamingStratergy extends ImplicitNamingStrategyJpaCompliantImpl {
	private static final long serialVersionUID = 6890554030847046251L;

	public Identifier determineCollectionTableName(ImplicitCollectionTableNameSource source) {
		String entityName = this.transformEntityName(source.getOwningEntityNaming());
		String propertyName = this.transformAttributePath(source.getOwningAttributePath());
		String nameTemp = entityName + '_' + propertyName;
		return this.toIdentifier(nameTemp, source.getBuildingContext());
	}

	public Identifier determineJoinTableName(ImplicitJoinTableNameSource source) {
		String propertyName = this.transformAttributePath(source.getAssociationOwningAttributePath());
		String nameTemp = source.getOwningPhysicalTableName() + '_' + propertyName;
		return this.toIdentifier(nameTemp, source.getBuildingContext());
	}

	public Identifier determineJoinColumnName(ImplicitJoinColumnNameSource source) {
		String name = null;
		if (source.getNature() != Nature.ELEMENT_COLLECTION && source.getAttributePath() != null) {
			name = this.transformAttributePath(source.getAttributePath());
		} else {
			name = this.transformEntityName(source.getEntityNaming());
		}

		return this.toIdentifier(name, source.getBuildingContext());
	}
}
