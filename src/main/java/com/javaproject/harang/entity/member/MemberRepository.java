package com.javaproject.harang.entity.member;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Integer>, MemberCustomerRepository {
    Optional<Member> findByUserIdAndPostId(Integer userId, Integer postId);

    Integer countAllByPostId(Integer postId);

    Optional<Member> findByPostId(Integer postId);

//    Optional<Member> findByUserId(Integer userId);


//
//    @Query("SELECT c FROM Member c WHERE c.postId=:postId")
//    List<Member> findAllByPostId(Integer postId);


    @Query("SELECT c FROM Member c WHERE c.userId=:userId")
    List<Member> findByAllUserId(Integer userId);


    }


