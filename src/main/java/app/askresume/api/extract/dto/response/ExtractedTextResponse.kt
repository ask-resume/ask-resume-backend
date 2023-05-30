package app.askresume.api.extract.dto.response

import io.swagger.v3.oas.annotations.media.Schema

data class ExtractedTextResponse(

    @field:Schema(
        description = "변환된 TEXT",
        example = "Hello, I'm backend developer Hong Gildong. Something, something, something..."
    )
    val text: String,

)