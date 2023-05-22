package app.askresume.api.resume.facade;

import app.askresume.api.resume.dto.request.GenerateExpectedQuestionRequest;
import app.askresume.api.resume.dto.request.ResumeDataRequest;
import app.askresume.api.resume.dto.response.WhatGeneratedResponse;
import app.askresume.api.resume.mapper.CareerYearMapper;
import app.askresume.api.resume.mapper.GenerateExpectedQuestionMapper;
import app.askresume.domain.prompt.constant.PromptType;
import app.askresume.domain.prompt.service.PromptService;
import app.askresume.external.gpt.service.GptService;
import app.askresume.domain.job.service.JobService;
import app.askresume.domain.resume.service.ResumeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ResumeFacade {

    private final ResumeService resumeService;
    private final GptService gptService;
    private final JobService jobService;
    private final GenerateExpectedQuestionMapper generateExpectedQuestionMapper;
    private final CareerYearMapper careerYearMapper;

    private final PromptService promptService;


    public WhatGeneratedResponse generate(final GenerateExpectedQuestionRequest request) {

        log.debug("이력서 생성 update request : {}", request.toString());

        final String job = jobService.findJobNameById(request.jobId()).getMasterName();
        final String difficulty = request.difficulty();
        final String careerYear = careerYearMapper.toCareer(request.careerYear());
        final String locale = request.locale();
        final List<ResumeDataRequest> resumeData = generateExpectedQuestionMapper.toResumeData(request.contents());

        String prompt = promptService.findByPromptType(PromptType.QUESTIONS_AND_MODEL_ANSWERS);

        WhatGeneratedResponse response = gptService.createdExpectedQuestionsAndAnswer(prompt, job, difficulty, careerYear, locale, resumeData);


        // request 내용, 생성된 질문 저장

        // 리스폰
        return response;
    }

}