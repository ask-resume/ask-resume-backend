package app.askresume.domain.job.service

import app.askresume.domain.job.model.Job
import app.askresume.domain.job.model.JobMaster
import app.askresume.domain.job.repository.JobMasterRepository
import app.askresume.domain.job.repository.JobRepository
import app.askresume.domain.locale.constant.LocaleType
import app.askresume.global.util.LoggerUtil.logger
import org.springframework.cache.annotation.CacheEvict
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class JobCommandService(
    private val jobMasterRepository: JobMasterRepository,
    private val jobRepository: JobRepository,
) {

    val log = logger()

    @CacheEvict(cacheNames = ["jobListCache"], allEntries = true)
    fun saveJobs(
        englishJobName: String,
        koreaJobName: String,
    ) {

        val jobMaster = jobMasterRepository.save(
            JobMaster(englishJobName)
        )

        jobRepository.save(
            Job(jobMaster = jobMaster, name = englishJobName, locale = LocaleType.EN)
        )

        jobRepository.save(
            Job(jobMaster = jobMaster, name = koreaJobName, locale = LocaleType.KO)
        )
    }

}
