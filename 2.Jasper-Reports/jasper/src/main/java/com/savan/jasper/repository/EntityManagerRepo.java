package com.savan.jasper.repository;

import org.hibernate.internal.util.collections.CollectionHelper;
import org.hibernate.transform.AliasToEntityMapResultTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;

import java.util.List;
import java.util.Map;

@Repository
public class EntityManagerRepo {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private JasperReportMstRepo jasperReportMstRepo;

    public List<Map<String, Object>> getDataForReport(String sqlQuery) {
        Query query = entityManager.createNativeQuery(sqlQuery);
        query.unwrap(org.hibernate.query.Query.class).setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);
        return query.getResultList();
    }
}

// String sqlQuery = "SELECT id, address, age, name, roll_number FROM STUDENT";