package com.kreyzon.jobsmanagement.utils;

import com.kreyzon.jobsmanagement.dto.RecruiterDto;
import com.kreyzon.jobsmanagement.model.Recruiter;

public class DtoUtils {

    public static RecruiterDto convertToDto(Recruiter recruiter) {
        RecruiterDto recruiterDto = RecruiterDto
                .builder()
                .id(recruiter.getId())
                .firstName(recruiter.getFirstName())
                .lastName(recruiter.getLastName())
                .fullName(recruiter.getFullName())
                .email(recruiter.getEmail())
                .companyName(recruiter.getCompanyName())
                .linkedinProfileUrl(recruiter.getLinkedinProfileUrl())
                .recruiterLevel(recruiter.getRecruiterLevel())
                .previousRecruiterReferenceId(recruiter.getPreviousRecruiterReferenceId())
                .previousRecruiterReferenceFullName(recruiter.getPreviousRecruiterReferenceFullName())
                .build();
        return recruiterDto;
    }
}
