package app.askresume.domain.submit.mapper

import app.askresume.domain.generative.interview.dto.InterviewMakerDto
import org.springframework.stereotype.Component

@Component
class SubmitDataMapper {

    fun mapToInterviewMakerDto(map: Map<String, Any>): InterviewMakerDto {
        return InterviewMakerDto(
            jobName = map["jobName"] as String,
            difficulty = map["difficulty"] as String,
            careerYear = map["careerYear"] as String,
            language = map["language"] as String,
            resumeType = map["resumeType"] as String,
            content = map["content"] as String,
        )

    }

}