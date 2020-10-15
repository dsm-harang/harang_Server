package com.javaproject.harang.entity.user.customer;


import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CustomerRepository extends CrudRepository<Customer, Integer> {
    Optional<Customer> findByUserId(String userId);
}
