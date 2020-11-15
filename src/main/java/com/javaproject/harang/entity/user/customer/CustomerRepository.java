package com.javaproject.harang.entity.user.customer;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends CrudRepository<Customer, Integer> {
    Optional<Customer> findByUserId(String userId);
    Optional<Customer> findByName(String name);

}
