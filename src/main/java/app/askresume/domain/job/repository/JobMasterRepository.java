package app.askresume.domain.job.repository;

import app.askresume.domain.job.model.JobMaster;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobMasterRepository extends JpaRepository<JobMaster, Long> {
}