package com.kreyzon.jobsmanagement.controller;

import com.kreyzon.jobsmanagement.dto.RecruiterDto;
import com.kreyzon.jobsmanagement.model.Recruiter;
import com.kreyzon.jobsmanagement.response.GenericResponse;
import com.kreyzon.jobsmanagement.service.RecruiterService;
import com.kreyzon.jobsmanagement.utils.DtoUtils;
import com.kreyzon.jobsmanagement.utils.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/recruiter")
@RequiredArgsConstructor
public class RecruiterController {

    private final RecruiterService recruiterService;

    @GetMapping("/recruiters")
    public ResponseEntity<List<Recruiter>> getAllRecruiters() {
        List<Recruiter> recruiters = recruiterService.findAll();
        return ResponseEntity.ok(recruiters);
    }

    @GetMapping("/search")
    public String displayRecruiterPage(Recruiter recruiter, Model model) {
        List<Recruiter> recruiterList = recruiterService.findAll();
        model.addAttribute("recruiterList", recruiterList);
        return "recruiter/recruiter-search";
    }

    @GetMapping("/add")
    public String addRecruiter(RecruiterDto recruiterDto, Model model, RedirectAttributes redirectAttributes) {
        GenericResponse response = recruiterService.addRecruiter(recruiterDto);
        if (response.isResultNOK())
            redirectAttributes.addFlashAttribute("message", response.getMessage());

        return "redirect:/recruiter/search";
    }

    @GetMapping("/insert")
    public String displayEmployeeInsertPage(Recruiter recruiter, Model model) {
        Utils.setPageTitle(model, "Recruiter - Add");

        List<Recruiter> recruiterList = recruiterService.findAll();
        model.addAttribute("recruiterList", recruiterList);

        return "recruiter/recruiter-insert";
    }

    @GetMapping("/update/{id}")
    public String displayUpdateRecruiterPage(@PathVariable("id") Integer id, Model model) {
        Recruiter recruiter = recruiterService.findById(id);
        RecruiterDto recruiterDto = DtoUtils.convertToDto(recruiter); // Implement this method to convert Recruiter to RecruiterDto
        List<Recruiter> recruiterList = recruiterService.findAll();

        model.addAttribute("recruiterDto", recruiterDto);
        model.addAttribute("recruiterList", recruiterList);
        return "recruiter/recruiter-update";
    }

    @PostMapping("/update")
    public String updateRecruiter(RecruiterDto recruiterDto, Model model, RedirectAttributes redirectAttributes) {
        GenericResponse response = recruiterService.updateRecruiter(recruiterDto);
        if (response.isResultNOK()) {
            redirectAttributes.addFlashAttribute("message", response.getMessage());
        }
        return "redirect:/recruiter/search";
    }

    @GetMapping("/delete/{id}")
    public String deleteRecruiter(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
        GenericResponse response = recruiterService.deleteRecruiter(id);
        if (response.isResultNOK()) {
            redirectAttributes.addFlashAttribute("message", response.getMessage());
        }
        return "redirect:/recruiter/search";
    }

}
