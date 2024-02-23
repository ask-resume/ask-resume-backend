package app.askresume.domain.job.service

import app.askresume.RANDOM
import app.askresume.domain.job.model.Job
import app.askresume.domain.job.model.JobMaster
import app.askresume.domain.job.repository.JobMasterRepository
import app.askresume.domain.job.repository.JobRepository
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
class JobCommandServiceMockkTest {

    @MockK
    private lateinit var jobMasterRepository: JobMasterRepository

    @MockK
    private lateinit var jobRepository: JobRepository

    @InjectMockKs
    private lateinit var jobCommandService: JobCommandService

    @Test
    fun `직업 영문명과 한글명을 받아, 직업 정보를 언어별로 저장한다`() {
        // given
        val englishJobName = RANDOM.nextString(5, 30)
        val koreaJobName = RANDOM.nextString(5, 30)

        val jobMaster = RANDOM.nextObject(JobMaster::class)
        val job = RANDOM.nextObject(Job::class)

        every { jobMasterRepository.save(any()) } returns jobMaster
        every { jobRepository.save(any()) } returns job

        // when
        jobCommandService.saveJobs(englishJobName, koreaJobName)

        // then
        verify { jobMasterRepository.save(any()) }
        verify(exactly = 2) { jobRepository.save(any()) }
    }

}