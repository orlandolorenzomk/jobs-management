package com.kreyzon.jobsmanagement.utils;

import com.kreyzon.jobsmanagement.model.Interview;
import com.kreyzon.jobsmanagement.model.Recruiter;
import com.kreyzon.jobsmanagement.repository.InterviewRepository;
import com.kreyzon.jobsmanagement.repository.RecruiterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class IdUtils {

    private final RecruiterRepository recruiterRepository;
    private final InterviewRepository interviewRepository;

    public Integer generateRecruiterId() {
        Optional<Recruiter> recruiterOptional = recruiterRepository.findTopByOrderByIdDesc();
        if (recruiterOptional.isEmpty())
            return 1;

        return recruiterOptional.get().getId() + 1;
    }

    public Integer generateInterviewId() {
        Optional<Interview> interviewOptional = interviewRepository.findTopByOrderByIdDesc();
        if (interviewOptional.isEmpty())
            return 1;

        return interviewOptional.get().getId() + 1;
    }
}
