package com.example.democrud.Service;

import com.example.democrud.Entity.Employee;
import com.example.democrud.Download.Helper;
import com.example.democrud.Repository.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

@org.springframework.stereotype.Service

public class Service {

    @Autowired
    private Repository repository;

    public List<Employee> getAll(){
        return (List<Employee>) repository.findAll();
    }

    public Employee getById(@PathVariable int id){
        return repository.getEmployeeByid(id);
    }

    // Add new Emp
    public String add(@RequestBody Employee employee){
        repository.save(employee);
        return "Created";
    }

    public String addMany(@RequestBody List<Employee> employee){
        repository.saveAll(employee);
        return "Created";
    }

    public String update(@RequestBody Employee employee){
        Employee existingEmp;
        existingEmp = repository.findById(employee.getId()).orElse(null);
        existingEmp.setName(employee.getName());
        existingEmp.setJobTitle(employee.getJobTitle());
        existingEmp.setPhone(employee.getPhone());
        existingEmp.setSalary(employee.getSalary());
        existingEmp.setAddress(employee.getAddress());

        repository.save(existingEmp);

        return "Updated";
    }

    public String delById(@PathVariable int id){
        repository.deleteById(id);
        return "Deletion Successful";
    }

    public String delAll(){
        repository.deleteAll();
        return "All record Deletion Successful";
    }

    // Get Mysql Table data as xlsx
    public ByteArrayInputStream getData() throws IOException {
        List<Employee> employees = (List<Employee>) repository.findAll();
        ByteArrayInputStream byteArrayInputStream = Helper.dataToExcel(employees);

        return byteArrayInputStream;
    }
}
