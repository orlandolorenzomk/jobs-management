package com.kreyzon.jobsmanagement.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Getter
@Setter
@Entity
@Table(name = "recruiter")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Recruiter {
    @Id
    @Column(name = "id_recruiter", nullable = false)
    private Integer id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "email")
    private String email;

    @Column(name = "company_name")
    private String companyName;

    @Column(name = "linkedin_profile_url", length = 500)
    private String linkedinProfileUrl;

    @Column(name = "deleted")
    private Boolean deleted;

    private String recruiterLevel;

    private Integer previousRecruiterReferenceId;

    private String previousRecruiterReferenceFullName;

    private Double requestedDailyRate;
}