package com.example.Hibernate.Repository;

import com.example.Hibernate.Entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Integer> {


}
