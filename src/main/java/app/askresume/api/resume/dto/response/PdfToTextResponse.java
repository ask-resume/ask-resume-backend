package app.askresume.api.resume.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class PdfToTextResponse {

    @Schema(description = "변환된 TEXT", example = "Hello, I'm backend developer Hong Gildong. Something, something, something...")
    private String text;
}
