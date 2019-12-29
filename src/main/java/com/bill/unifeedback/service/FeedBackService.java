package com.bill.unifeedback.service;

import com.bill.unifeedback.exceptions.FeedbackNotFound;
import com.bill.unifeedback.exceptions.IssueNotFound;
import com.bill.unifeedback.model.data.Feedback;
import com.bill.unifeedback.model.form.FeedBackForm;

import java.util.List;
import java.util.Optional;

public interface FeedBackService {
    List<Feedback> findAllFeedbacksByIssueId(Long topicId) throws IssueNotFound;
    Feedback findFeedback(Long feedbackId) throws FeedbackNotFound;
    void addFeedback(FeedBackForm feedBackForm) throws IssueNotFound;
    void UpdateFeedback(FeedBackForm feedBackForm) throws FeedbackNotFound,IssueNotFound;
    void deleteFeedback(Long issueId) throws IssueNotFound;
}
