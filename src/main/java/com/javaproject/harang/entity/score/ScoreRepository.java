package com.javaproject.harang.entity.score;//package com.javaproject.harang.entity.score;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ScoreRepository extends CrudRepository<Score, Integer> {
    //    Optional<Score> findByScore_target_name(String score_target_name);

    @Query("SELECT c FROM Score c WHERE c.userId=:userId")
    List<Score> findAllByUserId(Integer userId);
    Optional<Score> findByUserId(Integer userId);
}

