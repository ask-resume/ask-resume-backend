package app.askresume.domain.job.repository;

import app.askresume.domain.job.model.Job;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobRepository extends JpaRepository<Job, Long>, JobRepositoryCustom {
}