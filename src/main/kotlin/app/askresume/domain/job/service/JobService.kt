package app.askresume.domain.job.service

import app.askresume.domain.job.repository.JobMasterRepository
import app.askresume.domain.job.repository.findJobMaster
import app.askresume.global.util.LoggerUtil.logger
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class JobService(
    private val jobMasterRepository: JobMasterRepository,
) {

    val log = logger()

    fun findJobMasterName(id: Long): String {
        return jobMasterRepository.findJobMaster(id).masterName
    }
}
