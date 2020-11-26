package com.javaproject.harang.entity.Post;

import com.javaproject.harang.entity.user.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends CrudRepository<Post, Integer> {
    List<Post> findByTagContainsOrTitleContains(String tag, String title);
    Optional<Post> findByUser(User user);
    List<Post> findAllByUser(User user);
}
