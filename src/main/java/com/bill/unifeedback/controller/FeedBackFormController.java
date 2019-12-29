package com.bill.unifeedback.controller;


import com.bill.unifeedback.exceptions.EntityNotFound;
import com.bill.unifeedback.model.form.FeedBackForm;
import com.bill.unifeedback.service.FeedBackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/feedback/form")
public class FeedBackFormController {


    @Autowired
    FeedBackService feedBackService;

    @GetMapping("/{issueId}")
    public String initForm(@PathVariable("issueId") Long issueId, Model model) {

        FeedBackForm feedbackForm = new FeedBackForm();
        feedbackForm.setIssueId(issueId);
        model.addAttribute("feedbackForm", feedbackForm);
        return "feedbackForm";
    }

    @PostMapping("/add")
    public String sumbitFormForAdd(
            @ModelAttribute("feedbackForm") @Valid FeedBackForm feedbackForm,
            BindingResult result, final RedirectAttributes redirectAttributes
    ) throws EntityNotFound  {

        System.out.println(feedbackForm.getIssueId());

        if (result.hasErrors()) {
            return "feedbackForm";
        }
        else {
            feedBackService.addFeedback(feedbackForm);
            redirectAttributes.addFlashAttribute("alertClass", "alert-success");
            redirectAttributes.addFlashAttribute("msg", "New feedback added successfully!");
            return "redirect:/feedback/all/"+ feedbackForm.getIssueId();
        }
    }

    @PostMapping("/update/issue/{issueId}/feedback/{feedbackId}")
    public String submitForUpdate(
            @PathVariable("issueId") Long issueId,
            @PathVariable("feedbackId") Long feedbackId,
            @ModelAttribute("feedbackForm") @Valid FeedBackForm feedBackForm,
            BindingResult result, final RedirectAttributes redirectAttributes
    ) throws EntityNotFound {

        if (result.hasErrors()) {
            return "edit-feedback";
        } else {
            feedBackForm.setId(feedbackId);
            feedBackForm.setIssueId(issueId);
            feedBackService.UpdateFeedback(feedBackForm);
                    redirectAttributes.addFlashAttribute("alertClass", "alert-success");
            redirectAttributes.addFlashAttribute("msg", "Feedback updated successfully!");
            return "redirect:/feedback/all/"+ issueId;
        }
    }
}
