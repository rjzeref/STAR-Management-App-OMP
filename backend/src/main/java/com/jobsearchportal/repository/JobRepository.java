package com.jobsearchportal.repository;

import com.jobsearchportal.entity.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobRepository extends JpaRepository<Job, Long> {
    List<Job> findByCompanyId(Long companyId);
    List<Job> findByLocation(String Location);
    List<Job> findByPositionContainingIgnoreCase(String position);
    List<Job> findByLocationContainingIgnoreCase(String location);
    List<Job> findBySkillsContaining(String skill);
}