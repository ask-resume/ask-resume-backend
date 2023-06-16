package app.askresume.api.generative.mapper

import app.askresume.api.generative.dto.ResumeDataDto
import app.askresume.api.generative.dto.ResumeInformationDto
import app.askresume.domain.generative.interview.constant.ResumeType

fun toCareer(careerYear: Int): String {
    return when (careerYear) {
        0 -> "newcomer"
        10 -> "more than 10 years"
        else -> "${careerYear}year"
    }
}

fun toResumeData(contents: ResumeInformationDto): List<ResumeDataDto> {
    val mappings = mapOf(
        ResumeType.INTRODUCTION.value() to contents.introduction,
        ResumeType.CAREER.value() to contents.career,
        ResumeType.TECHNICAL.value() to contents.technical,
        ResumeType.PROJECT.value() to contents.project,
        ResumeType.OUTSIDE_ACTIVITIES.value() to contents.outsideActivities,
        ResumeType.AAC.value() to contents.aac,
    )

    return mappings.flatMap { (resumeType, list) ->
        list.map { o -> ResumeDataDto(resumeType, o.content) }
    }
}