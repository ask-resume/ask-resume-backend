package app.askresume.api.generative.mapper

import app.askresume.api.generative.vo.ResumeDataVo
import app.askresume.api.generative.vo.ResumeInformationVo
import app.askresume.domain.generative.interview.constant.ResumeType

fun toCareer(careerYear: Int): String {
    return when (careerYear) {
        0 -> "newcomer"
        10 -> "more than 10 years"
        else -> "${careerYear}year"
    }
}

fun resumeDataVoListOf(contents: ResumeInformationVo): List<ResumeDataVo> {
    val mappings = mapOf(
        ResumeType.INTRODUCTION.value() to contents.introduction,
        ResumeType.CAREER.value() to contents.career,
        ResumeType.TECHNICAL.value() to contents.technical,
        ResumeType.PROJECT.value() to contents.project,
        ResumeType.OUTSIDE_ACTIVITIES.value() to contents.outsideActivities,
        ResumeType.AAC.value() to contents.aac,
    )

    return mappings.flatMap { (resumeType, list) ->
        list.map { o -> ResumeDataVo(resumeType, o.content) }
    }
}