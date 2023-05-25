package com.kreyzon.jobsmanagement.repository;

import com.kreyzon.jobsmanagement.model.Recruiter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RecruiterRepository extends JpaRepository<Recruiter, Integer> {
    Optional<Recruiter> findTopByOrderByIdDesc();

    @Query(value = "SELECT * FROM recruiter WHERE deleted IS FALSE", nativeQuery = true)
    List<Recruiter> findAllNonDeleted();
}