package com.bill.unifeedback.controller;


import com.bill.unifeedback.exceptions.EntityNotFound;
import com.bill.unifeedback.model.form.IssueForm;
import com.bill.unifeedback.service.IssueService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/issue/form")
public class IssueFormController {

    private IssueService issueService;

    public IssueFormController(IssueService issueService) {
        this.issueService = issueService;
    }


    @ModelAttribute("issueForm")
    public IssueForm issueForm() {
        return new IssueForm();
    }
    @GetMapping
    public String showIssueForm() {
        return "issueForm";
    }


    @PostMapping("/add")
    public String submitForAdd(
            @ModelAttribute("issueForm") @Valid IssueForm issueForm,
            BindingResult result, final RedirectAttributes redirectAttributes
    ) {

        if (result.hasErrors()) {
            return "issueForm";
        } else {
            issueService.addIssue(issueForm);
            redirectAttributes.addFlashAttribute("alertClass", "alert-success");
            redirectAttributes.addFlashAttribute("msg", "New issue added successfully!");
            return "redirect:/issue/all?success";
        }
    }

    @PostMapping("/update/{issueId}")
    public String submitForUpdate(
            @PathVariable("issueId") Long issueId,
            @ModelAttribute("issueForm") @Valid IssueForm issueForm,
            BindingResult result, final RedirectAttributes redirectAttributes
    ) throws EntityNotFound {
        if (result.hasErrors()) {
            return "edit-issue";
        } else {
            issueService.updateIssue(issueForm);
            redirectAttributes.addFlashAttribute("alertClass", "alert-success");
            redirectAttributes.addFlashAttribute("msg", "Issue updated successfully!");
            return "redirect:/issue/all?success";
        }
    }
}
