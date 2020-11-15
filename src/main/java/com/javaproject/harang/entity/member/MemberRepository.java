package com.javaproject.harang.entity.member;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends CrudRepository<Member, Integer> {
    Optional<Member> findByUserIdAndPostId(Integer userId, Integer postId);
    Integer countAllByPostId(Integer postId);
}
