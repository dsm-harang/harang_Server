package com.javaproject.harang.entity.notify;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotifyRepository extends CrudRepository<Notify, Integer> {
}
