package com.savan.webflux.r2dbc.service;

import com.savan.webflux.r2dbc.entity.Student;
import com.savan.webflux.r2dbc.repo.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    public Flux<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public Flux<Student> getAllStudentsWithDelay() {
        return studentRepository.findAllStudentsWithDelay();
    }

}
