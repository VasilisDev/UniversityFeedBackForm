package com.bill.unifeedback.repository;

import com.bill.unifeedback.model.data.Issue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface IssueRepository extends JpaRepository<Issue,Long> {

}
