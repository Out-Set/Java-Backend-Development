package com.multiple.db.integrationDb.repo;

import com.multiple.db.integrationDb.entity.DemoMultiDb;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DummyDataRepo extends JpaRepository<DemoMultiDb, Integer> {

}
