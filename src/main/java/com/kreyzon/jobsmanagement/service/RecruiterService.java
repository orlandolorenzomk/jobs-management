package com.kreyzon.jobsmanagement.service;

import com.kreyzon.jobsmanagement.dto.RecruiterDto;
import com.kreyzon.jobsmanagement.model.Interview;
import com.kreyzon.jobsmanagement.model.Recruiter;
import com.kreyzon.jobsmanagement.repository.RecruiterRepository;
import com.kreyzon.jobsmanagement.response.GenericResponse;
import com.kreyzon.jobsmanagement.utils.Constant;
import com.kreyzon.jobsmanagement.utils.IdUtils;
import com.kreyzon.jobsmanagement.utils.Utils;
import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.NumberUtils;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RecruiterService {

    private final RecruiterRepository recruiterRepository;

    private final IdUtils idUtils;

    public List<Recruiter> findAll() {
        return recruiterRepository.findAllNonDeleted();
    }

    public GenericResponse addRecruiter(RecruiterDto recruiterDto) {
        Recruiter recruiter = Recruiter
                .builder()
                .id(idUtils.generateRecruiterId())
                .firstName(recruiterDto.getFirstName())
                .lastName(recruiterDto.getLastName())
                .fullName(recruiterDto.getFirstName() + " " + recruiterDto.getLastName())
                .email(recruiterDto.getEmail())
                .companyName(recruiterDto.getCompanyName())
                .linkedinProfileUrl(recruiterDto.getLinkedinProfileUrl())
                .recruiterLevel(recruiterDto.getRecruiterLevel())
                .requestedDailyRate(recruiterDto.getRequestedDailyRate())
                .deleted(false)
                .build();

        if (Utils.isNumeric(recruiterDto.getPreviousRecruiterReferenceId())) {
            Integer previousRecruiterReferenceId = Integer.valueOf(recruiterDto.getPreviousRecruiterReferenceId());

            if (recruiterDto.getPreviousRecruiterReferenceId() != null && previousRecruiterReferenceId > 0) {
                Optional<Recruiter> previousRecruiterOptional = recruiterRepository.findById(previousRecruiterReferenceId);
                if (previousRecruiterOptional.isEmpty())
                    return new GenericResponse(Constant.RESULT_NOK, "Previous recruiter doesn't exist", recruiterDto);

                Recruiter previousRecruiter = previousRecruiterOptional.get();
                recruiter.setPreviousRecruiterReferenceId(previousRecruiterReferenceId);
                recruiter.setPreviousRecruiterReferenceFullName(previousRecruiter.getFullName());
                recruiter.setRequestedDailyRate(previousRecruiter.getRequestedDailyRate());
            }
        }

        recruiterRepository.save(recruiter);

        return new GenericResponse(Constant.RESULT_OK, "Recruiter added", recruiterDto);
    }

    public Recruiter findById(Integer id) {
        return recruiterRepository
                .findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Id not found"));
    }

    public GenericResponse updateRecruiter(RecruiterDto recruiterDto) {
        GenericResponse response = new GenericResponse();

        // Find the existing recruiter by ID
        Recruiter existingRecruiter = recruiterRepository.findById(recruiterDto.getId()).orElse(null);

        if (existingRecruiter == null) {
            response.setResult(Constant.RESULT_NOK);
            response.setMessage("Recruiter not found.");
            return response;
        }

        // Update the recruiter information
        existingRecruiter.setFirstName(recruiterDto.getFirstName());
        existingRecruiter.setLastName(recruiterDto.getLastName());
        existingRecruiter.setFullName(recruiterDto.getFirstName() + " " + recruiterDto.getLastName());
        existingRecruiter.setEmail(recruiterDto.getEmail());
        existingRecruiter.setCompanyName(recruiterDto.getCompanyName());
        existingRecruiter.setLinkedinProfileUrl(recruiterDto.getLinkedinProfileUrl());
        existingRecruiter.setRequestedDailyRate(recruiterDto.getRequestedDailyRate());

        if (Utils.isNumeric(recruiterDto.getPreviousRecruiterReferenceId())) {
            Integer previousRecruiterReferenceId = Integer.valueOf(recruiterDto.getPreviousRecruiterReferenceId());
            if (recruiterDto.getPreviousRecruiterReferenceId() != null && previousRecruiterReferenceId > 0) {
                Optional<Recruiter> previousRecruiterOptional = recruiterRepository.findById(previousRecruiterReferenceId);
                if (previousRecruiterOptional.isEmpty())
                    return new GenericResponse(Constant.RESULT_NOK, "Previous recruiter doesn't exist", recruiterDto);

                Recruiter previousRecruiter = previousRecruiterOptional.get();
                existingRecruiter.setPreviousRecruiterReferenceId(previousRecruiterReferenceId);
                existingRecruiter.setPreviousRecruiterReferenceFullName(previousRecruiter.getPreviousRecruiterReferenceFullName());
                existingRecruiter.setRequestedDailyRate(previousRecruiter.getRequestedDailyRate());
            }
        }

        // Save the updated recruiter
        try {
            recruiterRepository.save(existingRecruiter);
            response.setResult(Constant.RESULT_OK);
            response.setMessage("Recruiter updated successfully.");
        } catch (Exception e) {
            response.setResult(Constant.RESULT_NOK);
            response.setMessage("Failed to update recruiter.");
        }

        return response;
    }

    public GenericResponse deleteRecruiter(Integer id) {
        GenericResponse response = new GenericResponse();

        // Find the recruiter by ID
        Recruiter recruiter = recruiterRepository.findById(id).orElse(null);

        if (recruiter == null) {
            response.setResult(Constant.RESULT_NOK);
            response.setMessage("Recruiter not found.");
            return response;
        }

        // Perform the delete operation
        try {
            // Instead of physically deleting, mark the recruiter as deleted
            recruiter.setDeleted(true);
            recruiterRepository.save(recruiter);
            response.setResult(Constant.RESULT_OK);
            response.setMessage("Recruiter deleted successfully.");
        } catch (Exception e) {
            response.setResult(Constant.RESULT_NOK);
            response.setMessage("Failed to delete recruiter.");
        }

        return response;
    }
}
