package app.askresume.api.job.usecase

import app.askresume.RANDOM
import app.askresume.api.job.mapper.JobMapper
import app.askresume.domain.job.dto.JobDto
import app.askresume.domain.job.service.JobReadOnlyService
import app.askresume.domain.locale.constant.LocaleType
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.BDDMockito.given
import org.mockito.BDDMockito.then
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.MockedStatic
import org.mockito.Mockito.mockStatic
import org.mockito.junit.jupiter.MockitoExtension


@ExtendWith(MockitoExtension::class)
class JobUseCaseTest {

    @Mock
    private lateinit var jobReadOnlyService: JobReadOnlyService


    @InjectMocks
    private lateinit var jobUseCase: JobUseCase

    // final class라서 test 불가
//    @Test
//    fun `LocalType에 해당하는 직업 리스트를 조회하고 mapper를 통해 response 형태로 가공한다2`() {
//        val jobMapper: MockedStatic<JobMapper> = mockStatic(JobMapper::class.java)
//
//        // given
//        val localeType = RANDOM.nextObject(LocaleType::class)
//        val jobList = RANDOM.nextList(JobDto::class)
//        val jobResponse = JobMapper.jobResponseListOf(jobList)
//
//        given(jobReadOnlyService.findJobs(localeType)).willReturn(jobList)
//        given(JobMapper.jobResponseListOf(jobList)).willReturn(jobResponse)
//
//        // when
//        val findJobs = jobUseCase.findJobs(localeType)
//
//        // then
//        then(jobReadOnlyService).should().findJobs(localeType)
//        then(JobMapper).should().jobResponseListOf(jobList)
//
//        jobMapper.close()
//    }
}

