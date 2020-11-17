package com.javaproject.harang.entity.member;

import com.javaproject.harang.payload.response.MyPostResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@Transactional

public class MemberCustomerRepositorylmpl implements MemberCustomerRepository{

    @Autowired
    EntityManager entityManager;

    @Override
    public List<MyPostResponse> findALLByuserId(Integer userId) {
        return entityManager.createQuery("SELECT c.postId FROM Member c " +
                "WHERE c.userId = :userId")
                .setParameter("userId", userId)
                .getResultList();
    }

    @Override
    public List<Member> findALLByPostId(int postId) {
        return entityManager.createQuery("SELECT m FROM Member m " +
                "WHERE m.postId = :postId")
                .setParameter("postId", postId)
                .getResultList();
    }
}
