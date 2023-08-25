package app.askresume.domain.submit.mapper

import app.askresume.domain.generative.interview.dto.InterviewMakerPdfSaveDto
import app.askresume.domain.generative.interview.dto.InterviewMakerSaveDto
import org.springframework.stereotype.Component

@Component
class SubmitDataMapper {

    fun interviewMakerSaveDtoOf(map: Map<String, Any>): InterviewMakerSaveDto {
        return InterviewMakerSaveDto(
            jobName = map["jobName"] as String,
            difficulty = map["difficulty"] as String,
            careerYear = map["careerYear"] as String,
            language = map["language"] as String,
            resumeType = map["resumeType"] as String,
            content = map["content"] as String,
        )
    }

    fun interviewMakerPdfSaveDtoOf(map: Map<String, Any>): InterviewMakerPdfSaveDto {
        return InterviewMakerPdfSaveDto(
            jobName = map["jobName"] as String,
            difficulty = map["difficulty"] as String,
            careerYear = map["careerYear"] as String,
            language = map["language"] as String,
            content = map["content"] as String,
        )
    }

}