package com.pk.integration.entity;

import java.util.Properties;

import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.boot.model.relational.QualifiedName;
import org.hibernate.boot.model.relational.QualifiedNameImpl;
import org.hibernate.dialect.Dialect;
import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment;
import org.hibernate.id.enhanced.SequenceStyleGenerator;
import org.hibernate.internal.util.config.ConfigurationHelper;
import org.hibernate.service.ServiceRegistry;

public class HibernateSequenceGenerator extends SequenceStyleGenerator {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final String ENTITY_TARGET_TABLE_NAME = "target_table";
	public static final String FULLY_QUALIFIED_ENTITY_NAME = "entity_name";


	protected QualifiedName determineSequenceName(Properties params, Dialect dialect, JdbcEnvironment jdbcEnv,
			ServiceRegistry serviceRegistry) {
		String sequencePerEntitySuffix = ConfigurationHelper.getString("sequence_per_entity_suffix", params, "_SEQ");
		String sequenceName = ConfigurationHelper.getBoolean("prefer_sequence_per_entity", params, false)
				? params.getProperty(ENTITY_TARGET_TABLE_NAME) + sequencePerEntitySuffix
				: "hibernate_sequence";
		sequenceName = DataBaseMappingUtil.abbreviateName(sequenceName);
		sequenceName = ConfigurationHelper.getString("sequence_name", params, sequenceName);
		return new QualifiedNameImpl(jdbcEnv.getCurrentCatalog(), jdbcEnv.getCurrentSchema(),
						new Identifier(sequenceName, false));
	}

}