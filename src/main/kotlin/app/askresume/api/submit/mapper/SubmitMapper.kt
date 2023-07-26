package app.askresume.api.submit.mapper

import app.askresume.api.submit.vo.SubmitResponse
import app.askresume.domain.submit.dto.SubmitDto
import app.askresume.domain.submit.model.Submit
import app.askresume.global.annotation.Mapper
import app.askresume.global.model.PageResponse
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl

@Mapper
class SubmitMapper {
    fun pageSubmitDtoToSubmitResponse(pagedSubmits: Page<SubmitDto>): PageResponse<SubmitResponse> {
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

}