package app.askresume.api.submit.mapper

import app.askresume.api.submit.dto.SubmitResponse
import app.askresume.domain.submit.model.Submit

fun toSubmitResponse(submit: Submit): SubmitResponse {
    return SubmitResponse(
        id = submit.id,
        title = submit.title,
        service = submit.serviceType,
        status = submit.submitStatus,
        createdAt = submit.createdAt.toOffsetDateTime(),
        updatedAt = submit.updatedAt.toOffsetDateTime(),
    )
}