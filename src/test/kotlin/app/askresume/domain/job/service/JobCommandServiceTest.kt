package app.askresume.domain.job.service

import app.askresume.RANDOM
import app.askresume.domain.job.model.Job
import app.askresume.domain.job.model.JobMaster
import app.askresume.domain.job.repository.JobMasterRepository
import app.askresume.domain.job.repository.JobRepository
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentMatchers.any
import org.mockito.BDDMockito.given
import org.mockito.BDDMockito.then
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.junit.jupiter.MockitoExtension

@ExtendWith(MockitoExtension::class)
class JobCommandServiceTest {

    @Mock
    private lateinit var jobMasterRepository: JobMasterRepository

    @Mock
    private lateinit var jobRepository: JobRepository

    @InjectMocks
    private lateinit var jobCommandService: JobCommandService

    @DisplayName("직업 정보를 언어별로 저장한다.")
    @Test
    fun saveJobsTest() {
        // given
        val englishJobName = RANDOM.nextString(5,30)
        val koreaJobName = RANDOM.nextString(5,30)

        val jobMaster = RANDOM.nextObject(JobMaster::class)
        given(jobMasterRepository.save(any(jobMaster::class.java))).willReturn(jobMaster)

        // when
        jobCommandService.saveJobs(englishJobName, koreaJobName)

        // then
        then(jobMasterRepository).should().save(any(jobMaster::class.java))
        verify(jobRepository, times(2)).save(any(Job::class.java))
    }

}