package app.askresume.domain.job.repository

import app.askresume.domain.job.model.Job
import org.springframework.data.jpa.repository.JpaRepository

interface JobRepository : JpaRepository<Job, Long>