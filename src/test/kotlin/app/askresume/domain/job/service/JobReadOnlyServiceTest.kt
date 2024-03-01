package app.askresume.domain.job.service

import app.askresume.RANDOM
import app.askresume.domain.job.dto.JobDto
import app.askresume.domain.job.model.JobMaster
import app.askresume.domain.job.repository.JobDataRepositoryQuery
import app.askresume.domain.job.repository.JobMasterRepository
import app.askresume.domain.locale.constant.LocaleType
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.BDDMockito.given
import org.mockito.BDDMockito.then
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import java.util.*

@ExtendWith(MockitoExtension::class)
class JobReadOnlyServiceTest {

    @Mock
    private lateinit var jobMasterRepository: JobMasterRepository

    @Mock
    private lateinit var jobDataRepositoryQuery : JobDataRepositoryQuery

    @InjectMocks
    private lateinit var jobReadOnlyService : JobReadOnlyService

    @Test
    fun `직업 ID로 이용해, 직업 고유명을 조회한다`() {
        // given
        val jobMasterId = RANDOM.nextLong()

        val jobMaster = RANDOM.nextObject(JobMaster::class)

        given(jobMasterRepository.findById(jobMasterId)).willReturn(Optional.of(jobMaster))

        // when
        val selectJobMaster = jobReadOnlyService.findJobMasterName(jobMasterId)

        // then
        then(jobMasterRepository).should().findById(jobMasterId)
        assertThat(selectJobMaster).isEqualTo(jobMaster.masterName)
    }


    @Test
    fun `locale 정보로 해당 언어의 직업을 조회한다`() {
        // given
        val localeType = RANDOM.nextObject(LocaleType::class)

        val list = RANDOM.nextList(JobDto::class)
        given(jobDataRepositoryQuery.findJobs(localeType)).willReturn(list)

        // when
        jobReadOnlyService.findJobs(localeType)

        // then
        then(jobDataRepositoryQuery).should().findJobs(localeType)
    }

}



