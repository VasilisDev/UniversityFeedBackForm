package com.bill.unifeedback.service.impl;

import com.bill.unifeedback.exceptions.FeedbackNotFound;
import com.bill.unifeedback.exceptions.IssueNotFound;
import com.bill.unifeedback.model.data.Feedback;
import com.bill.unifeedback.model.data.Issue;
import com.bill.unifeedback.model.form.FeedBackForm;
import com.bill.unifeedback.repository.FeedbackRepository;
import com.bill.unifeedback.repository.IssueRepository;
import com.bill.unifeedback.service.FeedBackService;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class FeedBackServiceImpl implements FeedBackService {

    private final
    FeedbackRepository feedbackRepository;


    private final
    IssueRepository issueRepository;

    public FeedBackServiceImpl(FeedbackRepository feedbackRepository, IssueRepository issueRepository) {
        this.feedbackRepository = feedbackRepository;
        this.issueRepository = issueRepository;
    }


    @Override
    public List<Feedback> findAllFeedbacksByIssueId(Long IssueId) throws IssueNotFound {
        Issue issue = issueRepository.findById(IssueId).orElseThrow(
                ()->new IssueNotFound("Issue with id: "+ IssueId + " not found !")
        );
        return feedbackRepository.findAllByIssueId(issue.getId());
    }


    @Override
    public Feedback findFeedback(Long feedbackId) throws FeedbackNotFound {
        return feedbackRepository.findById(feedbackId).orElseThrow(
                ()->new FeedbackNotFound("Feedback with id: "+ feedbackId + " not found !"));
    }


    @Override
    public void addFeedback(FeedBackForm feedBackForm) throws IssueNotFound {
        Issue issue = issueRepository.findById(feedBackForm.getIssueId()).orElseThrow(
                ()->new IssueNotFound("Issue with id: "+ feedBackForm.getIssueId() + " not found !")
        );

                 Feedback feedback = new Feedback();
                 feedback.setName(feedBackForm.getName());
                 feedback.setEmail(feedBackForm.getEmail());
                 feedback.setFeedback(feedBackForm.getFeedback());
                 issue.addfeedback(feedback);
                 feedbackRepository.save(feedback);

    }

    @Override
    public void UpdateFeedback(FeedBackForm feedBackForm) throws IssueNotFound, FeedbackNotFound {
        assert feedBackForm.getId() != null;
        Issue issue = issueRepository.findById(feedBackForm.getIssueId()).orElseThrow(
                ()->new IssueNotFound("Issue with id: "+ feedBackForm.getIssueId() + " not found !")
        );

         Feedback feedback =   feedbackRepository.findById(feedBackForm.getId()).orElseThrow(
                 ()->new FeedbackNotFound("Feedback with id: "+ feedBackForm.getId() + " not found !")

         );

                                feedback.setName(feedBackForm.getName());
                                feedback.setFeedback(feedBackForm.getFeedback());
                                feedback.setEmail(feedBackForm.getEmail());
                                issue.addfeedback(feedback);
                                feedbackRepository.save(feedback);
    }

    @Override
    public void deleteFeedback(Long issueId) throws IssueNotFound {
           Issue issue =  issueRepository.findById(issueId).orElseThrow(()->new IssueNotFound("Issue with id: "+issueId + " not found !")
           );
             Feedback feedback = issue.getFeedbacks().get(0);
             issue.removeFeedBack(feedback);
             issueRepository.save(issue);
             feedbackRepository.delete(feedback);
    }
}
