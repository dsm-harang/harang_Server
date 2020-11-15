package com.javaproject.harang.entity.user.admin;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdminRepository extends CrudRepository<Admin, Integer> {
    Optional<Admin> findByUserId(String userId);

}
