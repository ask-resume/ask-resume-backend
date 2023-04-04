package app.askresume.api.resume.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import org.hibernate.validator.constraints.Length;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

public record GenerateExpectedQuestionRequest(
        @NotNull
        @Schema(description = "직업 ID", example = "120", required = true)
        Long jobId,

        @NotBlank
        @Schema(description = "난이도", example = "medium", required = true)
        String difficulty,

        @Min(value = 0)
        @Max(value = 30)
        @Schema(description = "경력", example = "2", required = true)
        int careerYear,

        @Valid
        @Schema(description = "이력서 상세 내용", required = true)
        MyResume contents
) {
    public record MyResume(
            @Valid
            @Schema(description = "자기소개서")
            List<Content> introduction,

            @Valid
            @Schema(description = "경력이력")
            List<Content> career,

            @Valid
            @Schema(description = "기술스택")
            List<Content> technical,

            @Valid
            @Schema(description = "프로젝트 경험")
            List<Content> project,

            @Valid
            @Schema(description = "교육이력")
            List<Content> education,

            @Valid
            @Schema(description = "대외 활동")
            List<Content> outsideActivities,

            @Valid
            @Schema(description = "자격증,어학,수상내역(achievements_and_credentials)")
            List<Content> aac
    ) {
        public record Content(
                @Length(min = 100, max = 1000, message = "컨텐츠는 길이는 100자에서 1,000자 사이어야합니다.")
                String content
        ) {
        }
    }
}