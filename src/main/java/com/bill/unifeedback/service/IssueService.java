package com.bill.unifeedback.service;

import com.bill.unifeedback.exceptions.IssueNotFound;
import com.bill.unifeedback.model.data.Issue;
import com.bill.unifeedback.model.form.IssueForm;

import java.util.List;
import java.util.Optional;

public interface IssueService {

    List<Issue> findAllIssues() ;
    Issue findIssueById(Long id) throws IssueNotFound;
    void updateIssue(IssueForm issue) throws IssueNotFound;
    Issue  addIssue(IssueForm issue);
    void deleteIssue(Long issueId) throws IssueNotFound;

}
