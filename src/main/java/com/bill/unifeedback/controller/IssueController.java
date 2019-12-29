package com.bill.unifeedback.controller;


import com.bill.unifeedback.exceptions.EntityNotFound;
import com.bill.unifeedback.exceptions.IssueNotFound;
import com.bill.unifeedback.model.data.Issue;
import com.bill.unifeedback.model.form.IssueForm;
import com.bill.unifeedback.service.IssueService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/issue")
public class IssueController {

    private final
    IssueService issueService;

    public IssueController(IssueService issueService) {
        this.issueService = issueService;
    }

    @GetMapping("/all")
    public String GetAllIssues(Model model){
        List<Issue> issues = issueService.findAllIssues();
        model.addAttribute("issues", issues);
        return "issues";
    }

    @GetMapping("/{id}")
    public String getIssue(@PathVariable("id") Long id, Model model) throws EntityNotFound {
        Issue issue = issueService.findIssueById(id);
        model.addAttribute("issue", issue);
        return "issue";
    }

    @GetMapping("/update/{id}")
    public String updateIssue(@PathVariable("id") Long id, Model model) throws EntityNotFound {
             IssueForm issueForm =  new IssueForm();
             Issue issue = issueService.findIssueById(id);
             issueForm.setId(issue.getId());
             issueForm.setDescription(issue.getDescription());
             issueForm.setTitle(issue.getDescription());
             model.addAttribute("issueForm", issueForm);
        return "edit-issue";

    }

    @GetMapping("/delete/{id}")
    public String deleteIssue(@PathVariable("id") Long id,Model model,
                              final  RedirectAttributes redirectAttributes) throws EntityNotFound {

        issueService.deleteIssue(id);
        redirectAttributes.addFlashAttribute("alertClass", "alert-success");
        redirectAttributes.addFlashAttribute("msg", "Successfully delete issue with id "+ id +"!");
        model.addAttribute("issues",issueService.findAllIssues());
        return "redirect:/issue/all";
    }
}
