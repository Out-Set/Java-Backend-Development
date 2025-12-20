package com.pk.integration.repo;

import java.util.Map;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;

public interface EntityDao {
   //<T extends Entity> T find(Class<T> arg0, Serializable arg1);

   //Boolean contains(Entity arg0);

   <T> void persist(T arg0);

   //<T extends Entity> T update(T arg0);

   //<T extends Entity> T saveOrUpdate(T arg0);

   //<T extends Entity> void detach(T arg0);

   void delete(Entity arg0);

   //<T extends Entity> T get(EntityId arg0);

   void flush();

   //<T extends Entity> List<T> findAll(Class<T> arg0);

   //boolean entityExists(EntityId arg0);

   //<T> List<T> executeQuery(QueryExecutor<T> arg0);

   //<T> List<T> executeQuery(QueryExecutor<T> arg0, Integer arg1, Integer arg2);

   //<T> T executeQueryForSingleValue(QueryExecutor<T> arg0);

   //<T extends Entity> List<T> searchEntityOnFieldAndValue(Class<T> arg0, String arg1, String arg2);

   //Long getNextValue(String arg0);

   //Long getNextValue(String arg0, int arg1);

   //<T extends Entity> T load(Class<T> arg0, Serializable arg1);

   int updateQuery(String arg0, Map<String, Object> arg1);

   //<T> Long executeTotalRowsQuery(QueryExecutor<T> arg0);

   //<T extends Entity> List<Object[]> findAllWithSpecifiedColumns(Class<T> arg0, String[] arg1);

   void clearEntireCache();

   EntityManager getEntityManager();

   //List<Map<String, Object>> executeSingleInClauseHQLQuery(String arg0, String arg1, Collection<?> arg2);

   void clearEntityLevelCache(Class clazz);

   //List<Map<String, Object>> executeSingleInClauseHQLQuery(String arg0, String arg1, Collection<?> arg2, Map<String, ?> arg3);

   //<T> List<T> executeSingleInClauseHQLQuery(String arg0, String arg1, Collection<?> arg2, Map<String, ?> arg3, Class<T> arg4);

   //<T> List<T> executeSingleInClauseHQLQuery(String arg0, String arg1, Collection<?> arg2, Class<T> arg3);

   //<T extends Entity> T saveOrHibernateUpdate(T arg0);

   //<T extends Entity> T hibernateSaveOrUpdate(T arg0);

   //void executeSingleInClauseHQLQueryForUpdateDelete(String arg0, String arg1, Collection<?> arg2);

   //BaseMasterEntity findByUUID(Class arg0, String arg1);

   //BaseMasterEntity findByMasterUUID(Class arg0, String arg1);
}

