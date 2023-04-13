package app.askresume.api.resume.facade;

import app.askresume.api.resume.dto.request.GenerateExpectedQuestionRequest;
import app.askresume.api.resume.dto.request.ResumeDataRequest;
import app.askresume.api.resume.dto.response.WhatGeneratedResponse;
import app.askresume.api.resume.mapper.CareerYearMapper;
import app.askresume.api.resume.mapper.GenerateExpectedQuestionMapper;
import app.askresume.domain.gpt.service.GptService;
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


    public WhatGeneratedResponse generate(final GenerateExpectedQuestionRequest request) {

        final String job = jobService.findJobNameById(request.jobId()).getMasterName();
        final String difficulty = request.difficulty();
        final String careerYear = careerYearMapper.toCareer(request.careerYear());
        final List<ResumeDataRequest> resumeData = generateExpectedQuestionMapper.toResumeData(request.contents());

        // 나중에 모범답변? 으로 이름 바꿀지 고민해야함.
        WhatGeneratedResponse response = gptService.createdExpectedQuestionsAndAnswer(job, difficulty, careerYear, resumeData);


        // request 내용, 생성된 질문 저장

        // 리스폰
        return response;
    }

}