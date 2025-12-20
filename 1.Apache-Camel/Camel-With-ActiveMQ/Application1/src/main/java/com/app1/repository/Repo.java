package com.app1.repository;

import com.app1.entity.UserFullDtl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Repo extends JpaRepository<UserFullDtl, Integer> {

}
