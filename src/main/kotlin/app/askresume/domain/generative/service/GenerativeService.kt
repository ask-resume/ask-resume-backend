package app.askresume.domain.generative.service

import app.askresume.external.openai.dto.ChoicesDto


interface GenerativeService {

    fun saveGenerativeResult(
        submitDataId : Long,
        choices : List<ChoicesDto>
    )

}