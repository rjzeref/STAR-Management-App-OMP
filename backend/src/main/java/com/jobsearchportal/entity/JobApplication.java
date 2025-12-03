package com.jobsearchportal.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "job_applications")
public class JobApplication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Candidate ID is required")
    @Column(name = "candidate_id", nullable = false)
    private Long candidateId;

    @NotNull(message = "Job ID is required")
    @Column(name = "job_id", nullable = false)
    private Long jobId;

    @NotBlank(message = "Qualification is required")
    @Size(max = 255, message = "Qualification must be less than 255 characters")
    @Column(nullable = false, length = 255)
    private String qualification;

    @NotBlank(message = "Resume link is required")
    @Size(max = 255, message = "Resume link must be less than 255 characters")
    @Column(name = "resume_link", nullable = false, length = 255)
    private String resumeLink;

    @NotBlank(message = "Status is required")
    @Size(max = 50, message = "Status must be less than 50 characters")
    @Column(nullable = false, length = 50)
    private String status;

    @Column(name = "application_date")
    private LocalDateTime applicationDate;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // Constructors
    public JobApplication() {
    }

    public JobApplication(Long candidateId, Long jobId, String qualification, String resumeLink, String status) {
        this.candidateId = candidateId;
        this.jobId = jobId;
        this.qualification = qualification;
        this.resumeLink = resumeLink;
        this.status = status;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCandidateId() {
        return candidateId;
    }

    public void setCandidateId(Long candidateId) {
        this.candidateId = candidateId;
    }

    public Long getJobId() {
        return jobId;
    }

    public void setJobId(Long jobId) {
        this.jobId = jobId;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public String getResumeLink() {
        return resumeLink;
    }

    public void setResumeLink(String resumeLink) {
        this.resumeLink = resumeLink;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getApplicationDate() {
        return applicationDate;
    }

    public void setApplicationDate(LocalDateTime applicationDate) {
        this.applicationDate = applicationDate;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    // Lifecycle-like behavior for audit fields
    public void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        this.applicationDate = LocalDateTime.now();
    }

    public void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}