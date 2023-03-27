package app.askresume.api.resume.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class GenerateQuestionRequest {

    @Schema(description = "직업", example = "backend developer", required = true)
    @NotBlank
    private String job;

    @NotBlank
    @Schema(description = "난이도", example = "medium", required = true)
    private String difficulty;

    @Schema(description = "이력서 내용", example = "Hello, I'm backend developer Hong Gildong. Something, something, something...", required = true)
    @Length(min = 200, message = "이력서 내용이 너무 짧아, 질문을 생성 할 수 없습니다.")
    @Length(max = 10000, message = "이력서 내용이 너무 길어, 질문을 생성 할 수 없습니다.")
    @NotBlank
    private String content;

}
