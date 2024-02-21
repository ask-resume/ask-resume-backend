package app.askresume.api.job.usecase

import app.askresume.RANDOM
import app.askresume.api.job.mapper.JobMapper
import app.askresume.domain.job.dto.JobDto
import app.askresume.domain.job.service.JobReadOnlyService
import app.askresume.domain.locale.constant.LocaleType
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.mockkObject
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith


@ExtendWith(MockKExtension::class)
class JobUseCaseMockkTest {

    @MockK
    private lateinit var jobReadOnlyService: JobReadOnlyService

    @InjectMockKs
    private lateinit var jobUseCase: JobUseCase

    @Test
    fun `LocalType에 해당하는 직업 리스트를 조회하고 mapper를 통해 response 형태로 가공한다`() {
        mockkObject(JobMapper)

        // given
        val localeType = RANDOM.nextObject(LocaleType::class)
        val jobList = RANDOM.nextList(JobDto::class)
        val jobResponse = JobMapper.jobResponseListOf(jobList)

        every { jobReadOnlyService.findJobs(localeType) } returns jobList
        every { JobMapper.jobResponseListOf(jobList) } returns jobResponse

        // when
        jobUseCase.findJobs(localeType)

        // then
        verify { jobReadOnlyService.findJobs(localeType) }
        verify { JobMapper.jobResponseListOf(jobList) }
    }
}

