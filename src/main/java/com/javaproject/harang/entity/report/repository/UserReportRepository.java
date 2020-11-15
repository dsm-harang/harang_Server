package com.javaproject.harang.entity.report.repository;

import com.javaproject.harang.entity.report.UserReports;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserReportRepository extends CrudRepository<UserReports, Integer> {
    Optional<UserReports> findByUserIdAndTargetId(Integer userId, Integer targetId);
}
