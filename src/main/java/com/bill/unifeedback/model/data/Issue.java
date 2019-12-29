package com.bill.unifeedback.model.data;


import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.Instant;
import java.time.LocalDate;
import java.util.*;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "issue")
@Data
@EntityListeners(AuditingEntityListener.class)
public class Issue {



    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @Column(name = "title", nullable = false, length = 200)
    private String title;

    @Column(name = "description")
    private String description;

    @OneToMany(
            fetch = FetchType.EAGER,
            cascade = CascadeType.ALL ,
            orphanRemoval = true,
            mappedBy = "issue"
    )
    private List<Feedback> feedbacks = new ArrayList<>();

    @Column(nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    private Date createdAt;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @LastModifiedDate
    private Date updatedAt;

    public static Issue of(String title, String description){

        Issue issue= new Issue();
        issue.setTitle(title);
        issue.setDescription(description);
        return issue;
    }
    public void addfeedback(Feedback feedback) {
        feedbacks.add(feedback);
        feedback.setIssue(this);
    }

    public void removeFeedBack(Feedback feedback) {
        feedbacks.remove(feedback);
        feedback.setIssue(null);
    }
}
