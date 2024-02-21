package app.askresume.api.job.usecase

import app.askresume.RANDOM
import app.askresume.api.job.vo.SaveJobRequest
import app.askresume.domain.job.service.JobCommandService
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.BDDMockito.then
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension

@ExtendWith(MockitoExtension::class)
class AdminJobUseCaseTest {

    @Mock
    private lateinit var jobCommandService: JobCommandService

    @InjectMocks
    private lateinit var adminJobUseCase: AdminJobUseCase

    @Test
    fun `직업이름을 영문명, 한글명으로 받아 저장할 수 있습니다`() {
        // given
        val request = RANDOM.nextObject(SaveJobRequest::class)

        // when
        adminJobUseCase.save(request)

        // then
        then(jobCommandService).should().saveJobs(request.englishJobName, request.koreaJobName)
    }
}