package app.askresume.api.job.dto.response

import com.querydsl.core.annotations.QueryProjection

data class JobResponse @QueryProjection constructor(
    val id: Long,
    val name: String,
)

