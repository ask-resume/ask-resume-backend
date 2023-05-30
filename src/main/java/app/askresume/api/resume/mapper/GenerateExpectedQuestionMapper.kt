package app.askresume.api.resume.mapper

import app.askresume.api.resume.dto.request.GenerateExpectedQuestionRequest.MyResume
import app.askresume.api.resume.dto.request.ResumeDataRequest
import app.askresume.domain.resume.constant.ResumeType
import org.springframework.stereotype.Component

@Component
class GenerateExpectedQuestionMapper {

    fun toResumeData(contents: MyResume): List<ResumeDataRequest> {
        val mappings = mapOf(
            ResumeType.INTRODUCTION.value() to contents.introduction,
            ResumeType.CAREER.value() to contents.career,
            ResumeType.TECHNICAL.value() to contents.technical,
            ResumeType.PROJECT.value() to contents.project,
            ResumeType.OUTSIDE_ACTIVITIES.value() to contents.outsideActivities,
            ResumeType.AAC.value() to contents.aac,
        )

        return mappings.flatMap { (resumeType, list) ->
            list?.map { o -> ResumeDataRequest(resumeType, o.content) }
                ?: emptyList()
        }
    }

}

