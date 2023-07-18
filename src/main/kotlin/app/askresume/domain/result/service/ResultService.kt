package app.askresume.domain.result.service

import app.askresume.domain.result.model.Result
import app.askresume.domain.result.repository.ResultRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class ResultService(
    private val resultRepository: ResultRepository,
) {

    @Transactional
    fun saveResultResponseData(
        model: String,
        created: Long,
        promptTokens: Int,
        contentToken: Int,
        totalTokens: Int
    ) {

        resultRepository.save(
            Result(
                model = model,
                        created = created,
                        promptTokens = promptTokens,
                        contentToken = contentToken,
                        totalTokens = totalTokens,
            )
        )
    }
}