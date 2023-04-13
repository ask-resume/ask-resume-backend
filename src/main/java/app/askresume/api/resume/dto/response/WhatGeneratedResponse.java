package app.askresume.api.resume.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.ArrayList;
import java.util.List;

public record WhatGeneratedResponse(
        @Schema(description = "예상 질문 리스트", required = true)
        List<PredictionResponse> predictionResponse


) {
    public WhatGeneratedResponse() {
        this(new ArrayList<>());
    }
}
