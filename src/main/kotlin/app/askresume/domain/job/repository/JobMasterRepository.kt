package app.askresume.domain.job.repository

import app.askresume.domain.job.model.JobMaster
import org.springframework.data.jpa.repository.JpaRepository

interface JobMasterRepository : JpaRepository<JobMaster, Long>