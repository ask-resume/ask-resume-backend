package app.askresume.fixture

import app.askresume.api.generative.vo.InformationRequest
import app.askresume.domain.generative.interview.constant.DifficultyType
import app.askresume.domain.locale.constant.LocaleType

object InformationRequestFixture {

    fun informationRequest(
        jobId : Long = 1L,
        difficulty : DifficultyType = DifficultyType.MEDIUM,
        careerYear : Int = 3,
        language : LocaleType = LocaleType.EN,
    ) = InformationRequest(
        jobId = jobId,
        difficulty = difficulty,
        careerYear = careerYear,
        language = language,
    )
}