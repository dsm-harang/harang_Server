package com.javaproject.harang.entity.application;

import com.javaproject.harang.entity.application.eunm.Status;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ApplicationRepository extends CrudRepository<Application, Integer> {
    Optional<Application> findByUserIdAndPostIdAndStatus(Integer userId, Integer postId, Status status);
}
