package com.example.democrud.Repository;

import com.example.democrud.Entity.Employee;
import org.springframework.data.repository.CrudRepository;

@org.springframework.stereotype.Repository
public interface Repository extends CrudRepository<Employee, Integer> {

    Employee getEmployeeByid(int id);
}
