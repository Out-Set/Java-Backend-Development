package com.multiple.db.experianDb.repo;

import com.multiple.db.experianDb.entity.ExperianData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExperianDataRepo extends JpaRepository<ExperianData, Long> {

    String qry = """
            FROM ExperianData e WHERE e.panNumber = :txnRefNum AND
            TO_CHAR(e.creationTimeStamp, 'MM-YYYY') >= :duration
            ORDER BY e.creationTimeStamp
            """;
    @Query(value = qry)
    List<ExperianData> findData(@Param("txnRefNum")String txnRefNum, @Param("duration")String duration);
}
