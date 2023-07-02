package app.askresume.domain.job.service

import app.askresume.domain.job.exception.JobNotFoundException
import app.askresume.domain.job.repository.JobMasterRepository
import app.askresume.global.util.LoggerUtil.log
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class JobService(
    private val jobMasterRepository: JobMasterRepository,
) {

    val log = log()

    fun findJobMasterNameById(jobId: Long): String {
        return jobMasterRepository.findByIdOrNull(jobId)?.masterName
            ?: throw JobNotFoundException(jobId)
    }
}
