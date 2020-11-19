package com.javaproject.harang.entity.notify;

import com.javaproject.harang.entity.member.Member;
import com.javaproject.harang.entity.notify.NotifyType.NotifyType;
import com.javaproject.harang.entity.score.Score;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NotifyRepository extends CrudRepository<Notify, Integer> {
//    Optional<Notify> findByUserId(Integer userId);

    @Query("SELECT c FROM Notify c WHERE c.userId=:userId")
    List<Notify> findAllByUserId(Integer userId);
    
    Optional<Notify> findByUserIdAndPostIdAndType(Integer userId, Integer postId, NotifyType Type);

}
