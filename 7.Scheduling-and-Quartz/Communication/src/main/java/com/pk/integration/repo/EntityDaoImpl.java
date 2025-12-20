package com.pk.integration.repo;

import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

//import com.nucleus.persistence.sequence.DatabaseSequenceGenerator;

import jakarta.annotation.PostConstruct;
import jakarta.inject.Named;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

@Named("entityDao")
public class EntityDaoImpl implements EntityDao {
	private static final int ORACLE_BATCH_SIZE = 900;
	private static final Set<String> cacheableEntities = Collections.newSetFromMap(new ConcurrentHashMap());
	private static final Set<String> nonCacheableEntities = Collections.newSetFromMap(new ConcurrentHashMap());
	@PersistenceContext
	private EntityManager em;
	@Autowired
	private DataSource dataSource;
	// @Autowired
	// private DatabaseSequenceGenerator sequenceGenerator;
	protected JdbcTemplate jdbcTemplate;

	@PostConstruct
	protected void postContruct() {
		this.jdbcTemplate = new JdbcTemplate(this.dataSource);
	}

	public Boolean contains(Entity entity) {
		return Boolean.valueOf(this.em.contains(entity));
	}

	public EntityManager getEntityManager() {
		return this.em;
	}

	protected JdbcTemplate getJdbcTemplate() {
		return this.jdbcTemplate;
	}

	@Override
	public void flush() {
		this.em.flush();

	}
	
	public <T> void persist(T entity) {
	       this.em.persist(entity);
	    }
	 
	 
	    public void delete(Entity baseEntity) {
	       this.em.remove(baseEntity);
	    }
	
	/*
	 * public Long getNextValue(String sequenceName) { return
	 * this.sequenceGenerator.getNextValue(sequenceName); }
	 * 
	 * 
	 * public Long getNextValue(String sequenceName, int seqIncr) { return
	 * this.sequenceGenerator.getNextValue(sequenceName, seqIncr); }
	 */

	public int updateQuery(String queryString, Map<String, Object> map) {
		// NeutrinoValidator.notNull(queryString, "QueryString can not be null for
		// updating");
		Query qry = this.getEntityManager().createQuery(queryString);
		Iterator arg3 = map.keySet().iterator();
		while (arg3.hasNext()) {
			String key = (String) arg3.next();
			qry.setParameter(key, map.get(key));
		}
		return qry.executeUpdate();
	}

	@Override
	public void clearEntireCache() {
		this.em.getEntityManagerFactory().getCache().evictAll();

	}

	@Override
	public void clearEntityLevelCache(Class clazz) {
		this.em.getEntityManagerFactory().getCache().evict(clazz);

	}

}