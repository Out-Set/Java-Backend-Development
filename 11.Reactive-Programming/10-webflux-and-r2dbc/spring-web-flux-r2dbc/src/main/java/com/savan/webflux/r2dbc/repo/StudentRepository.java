package com.savan.webflux.r2dbc.repo;

import com.savan.webflux.r2dbc.entity.Student;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface StudentRepository extends ReactiveCrudRepository<Student, Integer> {

    // Making delay of 1-seconds on each row while fetching records
    @Query("SELECT pg_sleep(1.0), s.id, s.name, s.age FROM student s")
    Flux<Student> findAllStudentsWithDelay();
}
