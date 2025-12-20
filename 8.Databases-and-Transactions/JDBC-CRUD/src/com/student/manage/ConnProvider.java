package com.student.manage;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnProvider {

    static Connection conn;

    public static Connection create(){
        try {
            // load the driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // create connection
            String user = "root";
            String password = "@Root123$";
            String url = "jdbc:mysql://localhost:3306/Student";

            conn = DriverManager.getConnection(url, user, password);
        } catch (Exception e){
            e.printStackTrace();
        }

        return conn;
    }
}
