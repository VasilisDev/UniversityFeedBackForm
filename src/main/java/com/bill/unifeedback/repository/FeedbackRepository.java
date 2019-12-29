package com.bill.unifeedback.repository;

import com.bill.unifeedback.model.data.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback,Long> {


    List<Feedback> findAllByIssueId(Long issueId);

}
