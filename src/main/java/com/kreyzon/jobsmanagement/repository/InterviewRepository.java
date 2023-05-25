package com.kreyzon.jobsmanagement.repository;

import com.kreyzon.jobsmanagement.model.Interview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InterviewRepository extends JpaRepository<Interview, Integer> {

    @Query(value = "SELECT * FROM interview ORDER BY interview_date DESC", nativeQuery = true)
    List<Interview> findAllOrderByInterviewDateDesc();

    Optional<Interview> findTopByOrderByIdDesc();
}