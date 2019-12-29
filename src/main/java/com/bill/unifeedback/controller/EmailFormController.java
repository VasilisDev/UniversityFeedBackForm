package com.bill.unifeedback.controller;

import com.bill.unifeedback.exceptions.EntityNotFound;
import com.bill.unifeedback.model.form.EmailFeedback;
import com.bill.unifeedback.service.EmailService;
import com.bill.unifeedback.service.FeedBackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/email")
public class EmailFormController {


    @Autowired
    private EmailService emailService;
    @Autowired
    private FeedBackService feedBackService;

    @GetMapping("/issue/{issueId}/feedback/{feedbackId}")
    public String form(@PathVariable("issueId")Long id,@PathVariable("feedbackId")Long feedbackId, Model model) throws EntityNotFound {
        EmailFeedback emailFeedback = new EmailFeedback();
        emailFeedback.setIssueId(id);
        emailFeedback.setFeedbackId(feedbackId);
        String receiverEmail = feedBackService.findFeedback(feedbackId).getEmail();
        emailFeedback.setReceiverEmail(receiverEmail);
        model.addAttribute("emailForm", emailFeedback);
        return "emailForm";
    }

    @GetMapping
    public String form(Model model) {
        EmailFeedback emailFeedback = new EmailFeedback();
        model.addAttribute("emailForm", emailFeedback);
        return "emailForm";
    }


    @PostMapping
    public String send(@ModelAttribute("emailForm") EmailFeedback emailForm,
                       BindingResult result,final RedirectAttributes redirectAttributes) {

          if(result.hasErrors()){
              return "emailForm";
          }
          emailService.sendSimpleMessage(emailForm);
          redirectAttributes.addFlashAttribute("alertClass", "alert-success");
          redirectAttributes.addFlashAttribute("msg","An email sent to " + emailForm.getReceiverEmail() + " !" );
          return "redirect:/email/issue/"+emailForm.getIssueId()+"/feedback/"+emailForm.getFeedbackId();
    }
}
