package com.example.JDBC.SpringwebwithJDBC.DBManager;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.example.JDBC.SpringwebwithJDBC.DAOs.Person;
import com.example.JDBC.SpringwebwithJDBC.Requests.CreateRequest;

import jakarta.security.auth.message.callback.PrivateKeyCallback.Request;

public class DBOperations {

    // Variable 'connection' will be created in main memory due to volatile keyword.
    private static volatile Connection connection;

    public static Connection getConnection() throws SQLException{
        if(connection == null){
            synchronized(DBOperations.class){
                if(connection == null){
                    connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/person_4", "root", "@Root123$");
                }
            }
        }
        return connection;
    } 

    public static void closeConnection() throws SQLException{
        if(connection != null){
            synchronized(DBOperations.class){
                if(connection != null){
                    connection = null;
                }
            }
        }
    }
    
    public static void createTable(String name) throws SQLException {

        getConnection();

        Statement statement = connection.createStatement();
        boolean isCreated = statement.execute("CREATE TABLE " + name + "(id INT PRIMARY KEY AUTO_INCREMENT, name VARCHAR(20), age INT(3), address VARCHAR(50))");
        
        if(isCreated){
            System.out.println("Table" + name + " is successfully created.");
        }

        closeConnection();
    }

    public static void insertPerson(CreateRequest request) throws SQLException {
        
        getConnection();

        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO person VALUES(null, ?, ?, ?)");
        preparedStatement.setString(1, request.getName());
        preparedStatement.setInt(2, request.getAge());
        preparedStatement.setString(3, request.getAddress());

        int rows_affected = preparedStatement.executeUpdate();

        if(rows_affected > 0){
            System.out.println("Successfully inserted the record");
        } 
        else{
            System.out.println("Unable to inserted the record");
        }

        closeConnection();
    }


    // getting persons from DB
    public static List<Person> getPersons() throws SQLException{
        
        getConnection();

        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM person");
        
        List <Person> persons = new ArrayList<>();

        while(resultSet.next()){
            int id = resultSet.getInt(1);
            String name = resultSet.getString(2);
            int age = resultSet.getInt(3);
            String add = resultSet.getString(4);

            Person person = new Person(id, name, age, add);
            
            System.out.println(person);

            persons.add(person);
        }

        closeConnection();

        return persons;
    }


    public void getPersonById(CreateRequest request) throws SQLException {

        getConnection();

        
    }

    public static void deletePerson(){

    }

    public static void updatePerson(Person person){

    }
}
