package com.kreyzon.jobsmanagement.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * A DTO for the {@link com.kreyzon.jobsmanagement.model.Interview} entity
 */
@Data
public class InterviewDto implements Serializable {
    private final Integer id;
    private final String interviewType;
    private final String platform;
    private final String interviewDate;
    private final String notes;
    private final String endClient;
    private final Integer idRecruiter;
}