package com.javaproject.harang.entity.report.repository;

import com.javaproject.harang.entity.report.Report;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReportRepository extends CrudRepository<Report, Integer> {
    Optional<Report> findByUserIdAndPostId(Integer userId, Integer postId);
}
