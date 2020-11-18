package com.javaproject.harang.entity.score;//package com.javaproject.harang.entity.score;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;
import java.util.Optional;

@Repository
public interface ScoreRepository extends CrudRepository<Score, Integer> {

    @Query("SELECT c FROM Score c WHERE c.userId=:userId")
    List<Score> findAllByUserId(Integer userId);

    Optional<Score> findByUserId(Integer userId);

    @Query("SELECT c FROM Score c WHERE c.scoreTargetId=:scoreTargetId")
    List<Score> findByScoreTargetId(Integer scoreTargetId);

    Optional<Score> deleteByUserId(Integer userId);
    Optional<Score> findByUserIdAndScoreTargetId(Integer userId, Integer scoreTargetId);
}

