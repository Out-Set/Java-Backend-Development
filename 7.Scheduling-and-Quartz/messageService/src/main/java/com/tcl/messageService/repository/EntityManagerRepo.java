package com.tcl.messageService.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tcl.messageService.dto.DummyDto;
import com.tcl.messageService.entity.CustomCommAuditDtl;
import com.tcl.messageService.entity.CustomCommConfigMst;
import com.tcl.messageService.entity.CustomCommData;
import com.tcl.messageService.service.CustomCommConfigMstService;
import com.tcl.messageService.service.CustomCommDataService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository
public class EntityManagerRepo {

    @Autowired
    private EntityManager entityManager;
    @Autowired
    private CustomCommConfigMstService customCommConfigMstService;
    @Autowired
    private CustomCommDataService customCommDataService;

    public String prepareCommunicationData(String taskName){
        CustomCommConfigMst customCommConfigMst = customCommConfigMstService.findByCommTypeNameAndStatus(taskName, "A");
        if(customCommConfigMst != null){
            // Using entity manager execute the query
            String queryFromDb = customCommConfigMst.getQuery();
            Query query = entityManager.createNativeQuery(queryFromDb);
            List<Object[]> resultList = query.getResultList();

            // Create json string and extract 'to'
            for (Object[] singleResult : resultList) {
                String jsonString = resultToJsonString(singleResult);
                String to = extractValueOfKey("to", jsonString);

                // Save the records into customCommData
                CustomCommData customCommData = new CustomCommData();
                customCommData.setCreationTimeStamp(LocalDateTime.now());
                customCommData.setStatus("N");
                customCommData.setToCust(to);
                customCommData.setCommunicationType(customCommConfigMst.getCommunicationType());
                customCommData.setCommunicationName(taskName);
                customCommData.setCommData(jsonString);
                customCommDataService.create(customCommData);
            }

            // Update the status in customCommConfigMst
            /* Not Needed For Now
            customCommConfigMst.setStatus("X");
            customCommConfigMstService.update(customCommConfigMst);
            */

            return "Executed ...";
        } else {
            return "Configuration not found!";
        }
    }

    // Convert result to json string
    public String resultToJsonString(Object[] singleResult){
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonString = "";
        try {
            jsonString = objectMapper.writeValueAsString(singleResult);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return jsonString;
    }

    // Extract value with corresponding key from json string
    public String extractValueOfKey(String key, String strObj) {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode;
        try {
            rootNode = objectMapper.readTree(strObj);
            if (rootNode.has(key)) {
                return rootNode.get(key).asText();
            } else {
                return "";
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }


    //----------------------------------------- Other Work -----------------------------------------------------//
    public CustomCommAuditDtl getAuditLogs(String queryFromDb, String communicationType, String communicationTypeName) {
        String mmyyyy = "02-2024";
        //String sqlNativeQuery = "SELECT req.id, req.request_time_stamp, s.code AS source_system, req.service_identifier, req.status FROM service_req_res_audit_dtl req LEFT JOIN source_system_identifier_mst s ON req.source_system_identifier = s.id WHERE TO_CHAR(req.request_time_stamp, 'MM-YYYY') = :month_year";

        Query query = entityManager.createNativeQuery(queryFromDb);
        query.setParameter("month_year", mmyyyy);

        List<Object[]> obj = query.getResultList();
        return mapToAuditDashboardDto(obj, communicationType, communicationTypeName);
    }

    // Map list of object with pojo
    public CustomCommAuditDtl mapToAuditDashboardDto(List<Object[]> resultList, String communicationType, String communicationTypeName) {

        String logId = String.valueOf(resultList.get(0)[0]);
        String reqTimeStamp = String.valueOf(resultList.get(0)[1]);
        String sourceHost = String.valueOf(resultList.get(0)[2]);
        String serviceName = String.valueOf(resultList.get(0)[3]);
        String fetchStatus = String.valueOf(resultList.get(0)[4]);
        DummyDto dto = new DummyDto(logId, reqTimeStamp, sourceHost, serviceName, fetchStatus);

        CustomCommAuditDtl customCommAuditDtl = new CustomCommAuditDtl();

        customCommAuditDtl.setCreationTimeStamp(LocalDateTime.now());
        customCommAuditDtl.setStatus("not sent");
        customCommAuditDtl.setRequestBody("");
        customCommAuditDtl.setResponseBody("");
        customCommAuditDtl.setRequestTimeStamp(null);
        customCommAuditDtl.setResponseTimeStamp(null);
        customCommAuditDtl.setMessageBody(dto.toString());
        customCommAuditDtl.setCommunicationType(communicationType);
        customCommAuditDtl.setCommunicationTypeName(communicationTypeName);

        return customCommAuditDtl;
    }

    public List<DummyDto> mapToAuditDashboardDto1(List<Object[]> resultList) {
        List<DummyDto> dtoList = new ArrayList<>();

        System.out.println("1st result list :: "+resultList.get(0)[0]);
        System.out.println("1st result list :: "+resultList.get(0)[1]);
        System.out.println("1st result list :: "+resultList.get(0)[2]);
        System.out.println("1st result list :: "+resultList.get(0)[3]);
        System.out.println("1st result list :: "+resultList.get(0)[4]);
        System.out.println("1st result list :: "+resultList.get(0));

        for (Object[] result : resultList) {
            String logId = String.valueOf(result[0]);
            String reqTimeStamp = String.valueOf(result[1]);
            String sourceHost = String.valueOf(result[2]);
            String serviceName = String.valueOf(result[3]);
            String fetchStatus = String.valueOf(result[4]);

            DummyDto dto = new DummyDto(logId, reqTimeStamp, sourceHost, serviceName, fetchStatus);
            dtoList.add(dto);
        }
        return dtoList;
    }


    // Get Message template from template-table

}

