package com.activeMQ1.entity;

import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.ZonedDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@jakarta.persistence.Entity
@Table(name = "ActiveMQ")
public class Entity {

    @Id
    private int Eid;
    private String Action;
}
