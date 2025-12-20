package com.student.manage;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class StudentDao {

    public static boolean insertStudentToDB(Student student){

        boolean flag = false;

        try{
            // jdbc code
            Connection conn = ConnProvider.create();
            String query = "insert into student(sname, sphone, scity) values(?, ?, ?)";

            // prepared statement
            PreparedStatement pstmt = conn.prepareStatement(query);

            // set the values to parameter
            pstmt.setString(1, student.getStudentName());
            pstmt.setString(2, student.getStudentPhone());
            pstmt.setString(3, student.getStudentCity());

            // execute quesry
            pstmt.executeUpdate();
            flag = true;

        } catch (Exception e){
            e.printStackTrace();
        }

        return flag;
    }

    public static boolean updateExistingStudent(int stuID){

        boolean flag = false;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        try{
            // jdbc code
            Connection conn = ConnProvider.create();
            String query = "update student set sname=?, sphone=?, scity=? where sid=?";

            System.out.print("Enter Updated Name: ");
            String name = br.readLine();

            System.out.print("Enter Updated Phone: ");
            String phone = br.readLine();

            System.out.print("Enter Updated City: ");
            String city = br.readLine();

            // prepared statement
            PreparedStatement pstmt = conn.prepareStatement(query);


            // set the values to parameter
            pstmt.setString(1, name);
            pstmt.setString(2, phone);
            pstmt.setString(3, city);
            pstmt.setInt(4, stuID);

            // execute quesry
            pstmt.executeUpdate();
            flag = true;

        } catch (Exception e){
            e.printStackTrace();
        }

        return flag;
    }

    public static boolean deleteStudent(int stuid){

        boolean flag = false;

        try{
            // jdbc code
            Connection conn = ConnProvider.create();
            String query = "delete from student where sid=?";

            // prepared statement
            PreparedStatement pstmt = conn.prepareStatement(query);

            // set the values to parameter
            pstmt.setInt(1, stuid);


            // execute quesry
            pstmt.executeUpdate();
            flag = true;

        } catch (Exception e){
            e.printStackTrace();
        }

        return flag;
    }

    public static void showAllStudent(){

        try{
            // jdbc code
            Connection conn = ConnProvider.create();
            String query = "select * from student";

            // only statement
            Statement stmt = conn.createStatement();

            // execute query
            ResultSet rset = stmt.executeQuery(query);

            while(rset.next()){
                int id = rset.getInt(1);
                String name = rset.getString(2);
                String phone = rset.getString(3);
                String city = rset.getString(4);

                // printing on console
                System.out.println("ID: "+id);
                System.out.println("Name: "+name);
                System.out.println("Phone: "+phone);
                System.out.println("City: "+city);

                System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxx");
            }

        } catch (Exception e){
            e.printStackTrace();
        }
    }

}
