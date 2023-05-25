package com.kreyzon.jobsmanagement.dto;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * A DTO for the {@link com.kreyzon.jobsmanagement.model.Recruiter} entity
 */
@Data
@Builder
public class RecruiterDto implements Serializable {
    private final Integer id;
    private final String firstName;
    private final String lastName;
    private final String fullName;
    private final String email;
    private final String companyName;
    private final String linkedinProfileUrl;
    private final String recruiterLevel;
    private final Integer previousRecruiterReferenceId;
    private final String previousRecruiterReferenceFullName;
}