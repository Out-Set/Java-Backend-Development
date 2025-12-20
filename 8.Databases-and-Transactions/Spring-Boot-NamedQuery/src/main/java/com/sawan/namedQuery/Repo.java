package com.sawan.namedQuery;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Repo extends JpaRepository<Entity, Integer> {

    public List<Entity> findData();

    public List<Entity> findDataById(int id);

    public Entity findDataByIdAndName(int id, String name);

}
