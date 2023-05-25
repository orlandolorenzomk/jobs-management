package com.kreyzon.jobsmanagement.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "interview")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Interview {
    @Id
    @Column(name = "id_interview", nullable = false)
    private Integer id;

    @Column(name = "interview_type")
    private String interviewType;

    @Column(name = "platform")
    private String platform;

    @Column(name = "interview_date")
    private LocalDateTime interviewDate;

    @Column(name = "notes")
    private String notes;

    @Column(name = "end_client")
    private String endClient;

    private String result;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_recruiter")
    private Recruiter recruiter;
}