package app.askresume.api.resume.dto.response

import io.swagger.v3.oas.annotations.media.Schema

data class WhatGeneratedResponse(

    @field:Schema(
        description = "예상 질문 리스트",
        required = true
    )
    val predictionResponse: List<PredictionResponse>

) {
    constructor() : this(ArrayList())
}

