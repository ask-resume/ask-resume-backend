package app.askresume.api.extract.vo

import io.swagger.v3.oas.annotations.media.Schema

@Schema(name = "PDF TO TEXT 추출 Response")
data class ExtractedTextResponse(

    @field:Schema(
        description = "변환된 TEXT",
        example = "Hello, I'm backend developer Hong Gildong. Something, something, something..."
    ) val text: String,
)