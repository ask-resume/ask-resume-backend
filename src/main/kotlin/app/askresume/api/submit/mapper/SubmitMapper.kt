package app.askresume.api.submit.mapper

import app.askresume.api.submit.vo.InterviewMakerVo
import app.askresume.api.submit.vo.SubmitDetailResponse
import app.askresume.api.submit.vo.SubmitResponse
import app.askresume.domain.generative.interview.dto.InterviewMakerDto
import app.askresume.domain.submit.constant.ServiceType
import app.askresume.domain.submit.dto.SubmitDto
import app.askresume.api.PageResponse
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl

object SubmitMapper {
    fun submitResponseOf(pagedSubmits: Page<SubmitDto>): PageResponse<SubmitResponse> {
        return PageResponse(
            PageImpl(
                pagedSubmits.content.map {
                    SubmitResponse(
                        submitId = it.submitId,
                        title = it.title,
                        serviceType = it.serviceType,
                        submitStatus = it.submitStatus,
                        createdAt = it.createdAt,
                    )
                },
                pagedSubmits.pageable,
                pagedSubmits.totalElements,
            )
        )
    }

    fun submitDetailResponseOf(
        serviceType: ServiceType,
        findInterviewMaker: List<InterviewMakerDto>
    ): SubmitDetailResponse {
        return SubmitDetailResponse(
            serviceType = serviceType,
            interviewMakerList = findInterviewMaker.map {
                InterviewMakerVo(
                    question = it.question,
                    bestAnswer = it.bestAnswer,
                    satisfaction = it.satisfaction,
                )
            }
        )

    }

}