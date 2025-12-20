package com.example.demo.sqlDataToJson.test;

import com.example.demo.sqlDataToJson.service.SqlDataToJson;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Execute {

    @Autowired
    private SqlDataToJson sqlDataToJson;

//    @PostConstruct
    public  void execute(){
        sqlDataToJson.fetchData();
    }

    String qry = "select id as auditId, remote_address as remoteAdd, request_time_stamp as reqTimeStamp from service_req_res_audit_dtl";
    @PostConstruct
    public void getData(){
        sqlDataToJson.executeNativeQueryAsync(qry, 200);
    }
}
