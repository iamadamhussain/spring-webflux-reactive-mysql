package com.adam.reactivedemo.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import com.adam.reactivedemo.model.CourseWork;

import reactor.core.publisher.Mono;

@Repository
public interface CourseWorkRepository extends ReactiveCrudRepository<CourseWork, Long> {
    Mono<Void> deleteByStudentID(Long studentID);
}
