package app.askresume.api.resume.dto.response

import io.swagger.v3.oas.annotations.media.Schema

data class PredictionResponse(

    @field:Schema(
        description = "예상 질문",
        example = "Can you tell me about a time when you had difficul....",
        required = true
    )
    val question: String,

    @field:Schema(
        description = "모범 답안",
        example = "I once had to work on a project that involved int....",
        required = true
    )
    val bestAnswer: String,

)

