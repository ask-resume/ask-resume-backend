package app.askresume.domain.generative.interview.dto

import app.askresume.domain.submit.constant.Satisfaction
import com.querydsl.core.annotations.QueryProjection

data class InterviewMakerSaveDto(
    val jobName: String,
    val difficulty: String,
    val careerYear: String,
    val language: String,
    val resumeType: String,
    val content: String,
)

data class InterviewMakerPdfSaveDto(
    val jobName: String,
    val difficulty: String,
    val careerYear: String,
    val language: String,
    val content: String,
)

data class InterviewMakerDto @QueryProjection constructor(
    val interviewMakerId : Long,
    val question : String,
    val bestAnswer : String,
    val satisfaction : Satisfaction,
)

data class InterviewMakerResultDto(
    val question: String,
    val bestAnswer: String,
)

data class InterviewMakerResultDtoList(
    val interviews : List<InterviewMakerResultDto>
)