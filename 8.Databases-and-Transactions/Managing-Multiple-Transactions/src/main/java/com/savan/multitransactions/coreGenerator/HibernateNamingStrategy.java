package com.savan.multitransactions.coreGenerator;

import com.savan.multitransactions.dbUtility.DataBaseMappingUtil;
import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl;
import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment;

public class HibernateNamingStrategy extends PhysicalNamingStrategyStandardImpl {
	private static final long serialVersionUID = 6890554030847046251L;

	public Identifier toPhysicalTableName(Identifier name, JdbcEnvironment context) {
		String underScoreText = addUnderscores(name.getText());
		return new Identifier(this.transformToPluralForm(underScoreText), name.isQuoted());
	}

	public Identifier toPhysicalColumnName(Identifier name, JdbcEnvironment context) {
		String underScoreText = addUnderscores(name.getText());
		return new Identifier(this.transformToPluralForm(underScoreText), name.isQuoted());
	}

	public Identifier toPhysicalSequenceName(Identifier name, JdbcEnvironment context) {
		String underScoreText = addUnderscores(name.getText());
		return new Identifier(this.transformToPluralForm(underScoreText), name.isQuoted());
	}

	private String transformToPluralForm(String tableNameInSingularForm) {
		return DataBaseMappingUtil.abbreviateName(tableNameInSingularForm);
	}

	protected static String addUnderscores(String name) {
		StringBuilder buf = new StringBuilder(name.replace('.', '_'));

		for (int i = 1; i < buf.length() - 1; ++i) {
			if (Character.isLowerCase(buf.charAt(i - 1)) && Character.isUpperCase(buf.charAt(i))
					&& Character.isLowerCase(buf.charAt(i + 1))) {
				buf.insert(i++, '_');
			}
		}

		return buf.toString().toLowerCase();
	}
}