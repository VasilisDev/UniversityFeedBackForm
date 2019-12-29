package com.bill.unifeedback.service.impl;

import com.bill.unifeedback.exceptions.IssueNotFound;
import com.bill.unifeedback.model.builder.IssueBuilder;
import com.bill.unifeedback.model.data.Issue;
import com.bill.unifeedback.model.form.IssueForm;
import com.bill.unifeedback.repository.FeedbackRepository;
import com.bill.unifeedback.repository.IssueRepository;
import com.bill.unifeedback.service.IssueService;
import org.springframework.stereotype.Service;
import sun.util.resources.LocaleData;

import java.time.LocalDate;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Service
public class IssueServiceImpl implements IssueService {

    private final
    IssueRepository issueRepository;

    public IssueServiceImpl(IssueRepository issueRepository) {
        this.issueRepository = issueRepository;
    }


    @Override
    public List<Issue> findAllIssues() {
        return issueRepository.findAll();
    }

    @Override
    public Issue findIssueById(Long id) throws  IssueNotFound{
        return issueRepository.findById(id).orElseThrow(()-> new IssueNotFound("Issue with id :" + id + " not found!"));

    }

    @Override
    public void updateIssue(IssueForm issue) throws IssueNotFound {
        assert issue.getId() != null;

        Issue toModifyIssue =issueRepository.findById(issue.getId()).orElseThrow(()->
                        new IssueNotFound("Issue with id :" + issue.getId() + " not found!")
                );
            toModifyIssue.setTitle(issue.getTitle());
            toModifyIssue.setDescription(issue.getDescription());
            issueRepository.save(toModifyIssue);
    }

    @Override
    public Issue addIssue(IssueForm issueForm) {

        Issue issue = new IssueBuilder().with(issueBuilder -> {
            issueBuilder.setDescription(issueForm.getDescription());
            issueBuilder.setTitle(issueForm.getTitle());
        }).createIssue();

        return issueRepository.save(issue);
    }

    @Override
    public void deleteIssue(Long issueId) throws IssueNotFound {
        Issue issue = issueRepository.findById(issueId).orElseThrow(()->
                        new IssueNotFound("Issue with id :" + issueId + " not found!"));
                issueRepository.delete(issue);
    }
}
