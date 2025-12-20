package com.savan.jasperReports.jasperReport.service;

import net.sf.jasperreports.engine.JRDataset;
import net.sf.jasperreports.engine.JRQuery;
import net.sf.jasperreports.engine.JasperReport;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ExtractSqlQuery {

    // Extract the sql-queries
    public Set<String> extractSqlQueryFromJrXml(JasperReport jasperReport, Map<String, Object> params){

        Set<String> sqlQuerySet = new HashSet<>();

        // If .jrXml containing Single SQL-Query
        JRQuery query = jasperReport.getMainDataset().getQuery();
        String sqlQueryString = query.getText();
        System.out.println("Single-query :: "+sqlQueryString);
        sqlQuerySet.add(prepareFinalSqlQuery(params, sqlQueryString));

        // If .jrXml containing Multiple SQL-Queries
        JRDataset[] datasets = jasperReport.getDatasets();
        if(datasets != null){
            for (JRDataset ds : datasets) {
                String sqlQuery = ds.getQuery().getText();
                System.out.println("Multi-Query :: " + sqlQuery);
                sqlQuerySet.add(prepareFinalSqlQuery(params, sqlQuery));
            }
        }
        return sqlQuerySet;
    }

    // Set params after extracting the sql-queries
    public String prepareFinalSqlQuery(Map<String, Object> params, String sqlQueryString){
        Map<String, Object> parameters = new HashMap<>();
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            parameters.put(entry.getKey(), entry.getValue());
            System.out.println("Key: " + entry.getKey() + ", Value: " + entry.getValue() + ", " + entry.getValue().getClass().getName());

            // Modify the Sql-String
            String placeholder = "$P{" + entry.getKey() + "}";
            if (entry.getValue() instanceof String) {
                sqlQueryString = sqlQueryString.replace(placeholder, "'" + entry.getValue() + "'");
            }
            else {
                sqlQueryString = sqlQueryString.replace(placeholder, entry.getValue().toString());
            }
        }
        return sqlQueryString;
    }
}
