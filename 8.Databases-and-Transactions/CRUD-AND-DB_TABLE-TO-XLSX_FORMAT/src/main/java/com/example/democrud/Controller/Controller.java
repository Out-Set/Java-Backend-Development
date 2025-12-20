package com.example.democrud.Controller;

import com.example.democrud.Entity.Employee;
import com.example.democrud.Repository.Repository;
import com.example.democrud.Service.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

@RestController
public class Controller {

    @Autowired
    Repository repository;


    @Autowired
    private Service service;

    @GetMapping("/all")
    public List<Employee> getAll(){
        return (List<Employee>) service.getAll();
    }

    @GetMapping("/employee/{id}")
    public Employee getById(@PathVariable int id){
        return service.getById(id);
    }

    // Add new Emp
    @PostMapping("/add")
    public String add(@RequestBody Employee employee){
        service.add(employee);
        return "Created";
    }

    @PostMapping("/addMultiple")
    public String addMany(@RequestBody List<Employee> employee){
        service.addMany(employee);
        return "Created";
    }

    @PutMapping("update/{id}")
    public String update(@RequestBody Employee employee){
        Employee existingEmp;
        existingEmp = repository.findById(employee.getId()).orElse(null);
        existingEmp.setName(employee.getName());
        existingEmp.setJobTitle(employee.getJobTitle());
        existingEmp.setPhone(employee.getPhone());
        existingEmp.setSalary(employee.getSalary());
        existingEmp.setAddress(employee.getAddress());

        service.update(employee);

        return "Updated";
    }


    @DeleteMapping("/delete/{id}")
    public String delById(@PathVariable int id){
        service.delById(id);
        return "Deletion Successful";
    }

    @DeleteMapping("/deleteAll")
    public String delAll(){
        service.delAll();
        return "All record Deletion Successful";
    }


    // Convert to xlsx
    @RequestMapping("/excel")
    public ResponseEntity<Resource> toXLSX() throws IOException {
        String fileName = "Employees.xlsx";

        ByteArrayInputStream data = service.getData();
        InputStreamResource file = new InputStreamResource(data);

        ResponseEntity<Resource> body = ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename="+fileName)
                .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
                .body(file);

        return body;
    }
}
