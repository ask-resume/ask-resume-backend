package app.askresume.domain.job.service

import app.askresume.RANDOM
import app.askresume.domain.job.exception.JobNotFoundException
import app.askresume.domain.job.model.JobMaster
import app.askresume.domain.job.repository.JobDataRepositoryQuery
import app.askresume.domain.job.repository.JobMasterRepository
import app.askresume.domain.job.repository.findJobMasterById
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThatThrownBy

class JobReadOnlyServiceMockkAndKoTest : BehaviorSpec() {

    init {

        val jobMasterRepository = mockk<JobMasterRepository>()
        val jobDataRepositoryQuery = mockk<JobDataRepositoryQuery>()
        val jobReadOnlyService = JobReadOnlyService(jobMasterRepository, jobDataRepositoryQuery)

        Given("직업 ID를 이용해,") {
            val jobMasterId = RANDOM.nextLong()
            val jobMaster = RANDOM.nextObject(JobMaster::class)


            When("직업 테이블을 조회하여, 직업이 존재하면") {
                every { jobMasterRepository.findJobMasterById(jobMasterId) } returns jobMaster

                val selectJobMaster = jobReadOnlyService.findJobMasterName(jobMasterId)

                Then("직업 이름을 반환한다.") {
                    verify(exactly = 1) { jobMasterRepository.findJobMasterById(jobMasterId) }
                    selectJobMaster shouldBe jobMaster.masterName
                }
            }

            When("직업 테이블을 조회하여, 직업이 존재하지 않으면") {
                every { jobMasterRepository.findJobMasterById(- 1) }.throws(JobNotFoundException(jobMasterId))

                Then("JobNotFoundException가 발생한다.") {
                    assertThatThrownBy { jobMasterRepository.findJobMasterById(- 1) }
                        .isInstanceOf(JobNotFoundException::class.java)
                }
            }
        }
    }
}