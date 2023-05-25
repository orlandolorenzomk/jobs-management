package com.kreyzon.jobsmanagement.service;

import com.kreyzon.jobsmanagement.dto.InterviewDto;
import com.kreyzon.jobsmanagement.model.Interview;
import com.kreyzon.jobsmanagement.model.Recruiter;
import com.kreyzon.jobsmanagement.repository.InterviewRepository;
import com.kreyzon.jobsmanagement.repository.RecruiterRepository;
import com.kreyzon.jobsmanagement.response.GenericResponse;
import com.kreyzon.jobsmanagement.utils.Constant;
import com.kreyzon.jobsmanagement.utils.IdUtils;
import com.kreyzon.jobsmanagement.utils.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InterviewService {

    private final InterviewRepository interviewRepository;

    private final RecruiterRepository recruiterRepository;

    private final IdUtils idUtils;

    public List<Interview> findAll() {
        return interviewRepository.findAllOrderByInterviewDateDesc();
    }

    public Interview findById(Integer id) {
        return interviewRepository
                .findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Id not found"));
    }

    public GenericResponse addInterview(InterviewDto interviewDto) {
        Recruiter recruiter = recruiterRepository
                .findById(interviewDto.getIdRecruiter())
                .orElseThrow(() -> new IllegalArgumentException("Recruiter id not found"));

        Interview interview = Interview
                .builder()
                .id(idUtils.generateInterviewId())
                .interviewType(interviewDto.getInterviewType())
                .platform(interviewDto.getPlatform())
                .interviewDate(Utils.convertStringToLocalDateTime(interviewDto.getInterviewDate()))
                .notes(interviewDto.getNotes())
                .endClient(interviewDto.getEndClient())
                .recruiter(recruiter)
                .build();
        interviewRepository.save(interview);

        return new GenericResponse(Constant.RESULT_OK, "Interview added", interview);
    }
}
