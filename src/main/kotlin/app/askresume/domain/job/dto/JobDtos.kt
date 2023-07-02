package app.askresume.domain.job.dto

import com.querydsl.core.annotations.QueryProjection
import java.time.ZonedDateTime

data class JobDto @QueryProjection constructor(
    val id: Long,
    val name: String,
    val createdAt: ZonedDateTime,
    val updatedAt: ZonedDateTime,
)