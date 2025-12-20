package com.app2.repository;

import com.app2.entity.UserDtl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Repo extends JpaRepository<UserDtl, Integer> {

}
