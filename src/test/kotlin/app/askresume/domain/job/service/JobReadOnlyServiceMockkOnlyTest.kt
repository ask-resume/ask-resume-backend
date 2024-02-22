package app.askresume.domain.job.service

import app.askresume.RANDOM
import app.askresume.domain.job.model.JobMaster
import app.askresume.domain.job.repository.JobDataRepositoryQuery
import app.askresume.domain.job.repository.JobMasterRepository
import app.askresume.domain.job.repository.findJobMasterById
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
class JobReadOnlyServiceMockkOnlyTest {

    @MockK
    private lateinit var jobMasterRepository: JobMasterRepository

    @MockK
    private lateinit var jobDataRepositoryQuery: JobDataRepositoryQuery

    @InjectMockKs
    private lateinit var jobReadOnlyService: JobReadOnlyService

    @Test
    fun `직업 ID로 이용해, 직업 고유명을 조회한다`() {
        // given
        val jobMasterId = RANDOM.nextLong()
        val jobMaster = RANDOM.nextObject(JobMaster::class)

        every { jobMasterRepository.findJobMasterById(jobMasterId) } returns (jobMaster)

        // when
        val selectJobMaster = jobReadOnlyService.findJobMasterName(jobMasterId)

        // then
        verify(exactly = 1) { jobMasterRepository.findJobMasterById(jobMasterId) }
        assertThat(selectJobMaster).isEqualTo(jobMaster.masterName)
    }

}



