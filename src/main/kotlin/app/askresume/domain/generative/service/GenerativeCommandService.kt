package app.askresume.domain.generative.service

import app.askresume.external.openai.dto.ChoicesDto


interface GenerativeCommandService {

    fun saveGenerativeResult(
        submitId : Long,
        submitDataId : Long,
        choices : List<ChoicesDto>
    )
}