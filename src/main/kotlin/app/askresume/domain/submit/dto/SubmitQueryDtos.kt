package app.askresume.domain.submit.dto

import app.askresume.domain.submit.constant.ServiceType
import com.querydsl.core.annotations.QueryProjection


data class FirstSubmittedDto @QueryProjection constructor(
    val submitId: Long,
    val submitDataId: Long,
    val serviceType: ServiceType,
    val parameter: Map<String, Any> = mapOf(),
)
