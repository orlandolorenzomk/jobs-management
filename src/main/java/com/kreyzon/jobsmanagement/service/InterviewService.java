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

    public GenericResponse updateInterview(InterviewDto interviewDto) {
        GenericResponse response = new GenericResponse();

        // Find the existing interview by ID
        Interview existingInterview = interviewRepository.findById(interviewDto.getId())
                .orElseThrow(() -> new IllegalArgumentException("Interview not found."));

        // Update the interview information
        existingInterview.setInterviewType(interviewDto.getInterviewType());
        existingInterview.setPlatform(interviewDto.getPlatform());
        existingInterview.setInterviewDate(Utils.convertStringToLocalDateTime(interviewDto.getInterviewDate()));
        existingInterview.setNotes(interviewDto.getNotes());
        existingInterview.setEndClient(interviewDto.getEndClient());
        existingInterview.setResult(interviewDto.getResult());

        // Find the recruiter by ID
        Recruiter recruiter = recruiterRepository.findById(interviewDto.getIdRecruiter())
                .orElseThrow(() -> new IllegalArgumentException("Recruiter not found."));

        existingInterview.setRecruiter(recruiter);

        // Save the updated interview
        try {
            interviewRepository.save(existingInterview);
            response.setResult(Constant.RESULT_OK);
            response.setMessage("Interview updated successfully.");
        } catch (Exception e) {
            response.setResult(Constant.RESULT_NOK);
            response.setMessage("Failed to update interview.");
        }

        return response;
    }

    public GenericResponse deleteInterview(Integer id) {
        GenericResponse response = new GenericResponse();

        // Find the interview by ID
        Interview interview = interviewRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Interview not found."));

        // Perform the delete operation
        try {
            interviewRepository.delete(interview);
            response.setResult(Constant.RESULT_OK);
            response.setMessage("Interview deleted successfully.");
        } catch (Exception e) {
            response.setResult(Constant.RESULT_NOK);
            response.setMessage("Failed to delete interview.");
        }

        return response;
    }
}
