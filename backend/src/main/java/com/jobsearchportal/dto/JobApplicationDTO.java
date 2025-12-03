package com.jobsearchportal.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class JobApplicationDTO {

    private Long id;

    @NotNull(message = "Candidate ID is required")
    private Long candidateId;

    @NotNull(message = "Job ID is required")
    private Long jobId;

    @NotBlank(message = "Qualification is required")
    @Size(max = 255, message = "Qualification must be less than 255 characters")
    private String qualification;

    @NotBlank(message = "Resume link is required")
    @Size(max = 255, message = "Resume link must be less than 255 characters")
    private String resumeLink;

    @NotBlank(message = "Status is required")
    @Size(max = 50, message = "Status must be less than 50 characters")
    private String status;

    // Constructors
    public JobApplicationDTO() {
    }

    public JobApplicationDTO(Long id, Long candidateId, Long jobId, String qualification, String resumeLink, String status) {
        this.id = id;
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
}