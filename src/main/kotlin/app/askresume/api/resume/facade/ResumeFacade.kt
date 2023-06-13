package app.askresume.api.resume.facade

import app.askresume.api.resume.dto.request.GenerateExpectedQuestionRequest
import app.askresume.api.resume.dto.response.WhatGeneratedResponse
import app.askresume.api.resume.mapper.CareerYearMapper
import app.askresume.api.resume.mapper.GenerateExpectedQuestionMapper
import app.askresume.domain.job.service.JobService
import app.askresume.domain.prompt.constant.PromptType
import app.askresume.domain.prompt.service.PromptService
import app.askresume.domain.resume.service.ResumeService
import app.askresume.external.gpt.service.GptService
import app.askresume.global.util.LoggerUtil.log
import org.springframework.stereotype.Service


@Service
class ResumeFacade(
    private val resumeService: ResumeService,
    private val gptService: GptService,
    private val jobService: JobService,
    private val generateExpectedQuestionMapper: GenerateExpectedQuestionMapper,
    private val careerYearMapper: CareerYearMapper,
    private val promptService: PromptService,
) {

    val log = log()

    fun generate(request: GenerateExpectedQuestionRequest): WhatGeneratedResponse {
        val job = jobService.findJobNameById(request.jobId).masterName
        val difficulty = request.difficulty
        val careerYear = careerYearMapper.toCareer(request.careerYear)
        val locale = request.locale
        val resumeData = generateExpectedQuestionMapper.toResumeData(request.contents)

        val prompt = promptService.findByPromptType(PromptType.QUESTIONS_AND_MODEL_ANSWERS)

        // request 내용, 생성된 질문 저장

        // 리스폰
        return gptService.createdExpectedQuestionsAndAnswer(prompt, job, difficulty, careerYear, locale, resumeData)
    }
}