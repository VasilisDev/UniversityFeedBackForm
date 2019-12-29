package com.bill.unifeedback.model.builder;

import com.bill.unifeedback.model.data.Issue;
import lombok.Data;
import java.util.function.Consumer;

@Data
public class IssueBuilder {

        private String title;
        private String description;

        public IssueBuilder with( Consumer<IssueBuilder> builderFunction) {
             builderFunction.accept(this);
            return this;
        }

        public Issue createIssue() {
            return Issue.of(title, description);
        }
}