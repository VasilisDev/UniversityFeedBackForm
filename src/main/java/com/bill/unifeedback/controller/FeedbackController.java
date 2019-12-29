package com.bill.unifeedback.controller;


import com.bill.unifeedback.exceptions.EntityNotFound;
import com.bill.unifeedback.model.data.Feedback;
import com.bill.unifeedback.model.form.FeedBackForm;
import com.bill.unifeedback.service.FeedBackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/feedback")
public class FeedbackController {

    @Autowired
    FeedBackService feedBackService;
    
    @GetMapping("/all/{issueId}")
    public String getIndex(@PathVariable("issueId") Long issueId, Model model) throws EntityNotFound{
        List<Feedback> feedbacks = Optional.ofNullable(feedBackService.findAllFeedbacksByIssueId(issueId)).orElseGet(ArrayList::new);
        model.addAttribute("issueId", issueId);
        model.addAttribute("feedbacks", feedbacks);
        return "feedback";
    }
    @GetMapping("/delete/issue/{issueId}/feedback/{feedBackId}")
    public String delete(@PathVariable("issueId")Long issueId ,
                         @PathVariable("feedBackId")Long feedbackId ,
                         Model model, final RedirectAttributes redirectAttributes) throws EntityNotFound{


        feedBackService.deleteFeedback(issueId);
        redirectAttributes.addFlashAttribute("alertClass", "alert-success");
        redirectAttributes.addFlashAttribute("issueId",issueId);
        redirectAttributes.addFlashAttribute("msg", "Successfully delete feedback with id "+ feedbackId +"!");
        List<Feedback> feedbacks = Optional.ofNullable(feedBackService.findAllFeedbacksByIssueId(issueId)).orElseGet(ArrayList::new);
        model.addAttribute("feedbacks",feedbacks);
        return "redirect:/feedback/all/" + issueId;

    }


    @GetMapping("/update/issue/{issueId}/feedback/{feedbackId}")
    public String updateFeedback(
            @PathVariable("issueId") Long issueId,@PathVariable("feedbackId") Long feedbackId, Model model) throws EntityNotFound {

        Feedback feedback = feedBackService.findFeedback(feedbackId);
        FeedBackForm feedbackForm = new FeedBackForm();
        feedbackForm.setId(feedbackId);
        feedbackForm.setIssueId(issueId);
        feedbackForm.setName(feedback.getName());
        feedbackForm.setEmail(feedback.getEmail());
        feedbackForm.setFeedback(feedback.getFeedback());
        model.addAttribute("feedbackForm", feedbackForm);
        return "edit-feedback";

    }
}
