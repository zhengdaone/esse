package com.fl.esse.repository;

import com.fl.esse.entity.HHWUsers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<HHWUsers, Integer>, QuerydslPredicateExecutor<HHWUsers> {
}
