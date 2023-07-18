package app.askresume.domain.generative.interview.dto

data class InterviewMakerDto(
    val jobName: String,
    val difficulty: String,
    val careerYear: String,
    val language: String,
    val resumeType: String,
    val content: String,
)

data class InterviewMakerResultDto(
    val question: String,
    val bestAnswer: String,
)

data class InterviewMakerResultDtoList(
    val interviews : List<InterviewMakerResultDto>
)