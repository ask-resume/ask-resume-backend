package app.askresume.domain.job.repository

import app.askresume.domain.job.exception.JobNotFoundException
import app.askresume.domain.job.model.JobMaster
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.findByIdOrNull

fun JobMasterRepository.findJobMaster(id: Long) : JobMaster =
    findByIdOrNull(id) ?: throw JobNotFoundException(id)

interface JobMasterRepository : JpaRepository<JobMaster, Long>