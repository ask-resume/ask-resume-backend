package app.askresume.domain.result.service

import app.askresume.RANDOM
import app.askresume.domain.result.model.Result
import app.askresume.domain.result.repository.ResultRepository
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.BDDMockito.*
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension

@ExtendWith(MockitoExtension::class)
class ResultServiceTest {

    @Mock
    private lateinit var resultRepository: ResultRepository

    @InjectMocks
    private lateinit var resultService: ResultService

    @DisplayName("생성된 결과의 리소스 정보를 저장한다.")
    @Test
    fun saveResultResponseDataTest() {
        // given
        val model = RANDOM.nextString()
        val created = RANDOM.nextLong()
        val promptTokens = RANDOM.nextInt()
        val contentToken = RANDOM.nextInt()
        val totalTokens = RANDOM.nextInt()

        val result = RANDOM.nextObject(Result::class)

        given(resultRepository.save(any())).willReturn(result)

        // when
        resultService.saveResultResponseData(model, created, promptTokens, contentToken, totalTokens)

        // then
        then(resultRepository).should().save(any())
    }

}