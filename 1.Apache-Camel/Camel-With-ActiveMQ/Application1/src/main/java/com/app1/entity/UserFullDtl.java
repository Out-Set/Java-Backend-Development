package com.app1.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.springframework.beans.factory.annotation.Value;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user_full_dtl")
//@JsonPropertyOrder({ "id", "name", "add", "jobTitle", "sal", "phoneNo" }) //for csv to pojo(json) conversion
public class UserFullDtl {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "address")
    private String add;

    @Column(name = "job_title")
    private String jobTitle;

    @Column(name = "phone_no")
    private int phoneNo;

    @Column(name = "salary")
    private int sal;

}
