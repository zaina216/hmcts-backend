package uk.gov.hmcts.reform.dev.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uk.gov.hmcts.reform.dev.models.ExampleTask;

@Repository
public interface ExampleTaskRepository extends JpaRepository<ExampleTask, Integer> {
}
