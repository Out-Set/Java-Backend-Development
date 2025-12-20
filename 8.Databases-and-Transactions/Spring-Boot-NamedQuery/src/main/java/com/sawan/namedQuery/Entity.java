package com.sawan.namedQuery;

import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.NamedNativeQuery;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@jakarta.persistence.Entity
@NamedNativeQuery(
        name = "Entity.findData",
        query = "select * from Named_Query",
        resultClass = Entity.class
)
@NamedNativeQuery(
        name = "Entity.findDataById",
        query = "select * from Named_Query where id = ?",
        resultClass = Entity.class
)
@NamedNativeQuery(
        name = "Entity.findDataByIdAndName",
        query = "select * from Named_Query where id = ? AND name = ?",
        resultClass = Entity.class
)
@Table(name = "Named_Query")
public class Entity {

    @Id
    private int id;
    private String name;
}
