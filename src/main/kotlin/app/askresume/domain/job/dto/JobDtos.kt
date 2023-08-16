package app.askresume.domain.job.dto

import com.querydsl.core.annotations.QueryProjection
import java.time.LocalDateTime

data class JobDto @QueryProjection constructor(
    val id: Long,
    val name: String,
    val createdAt: LocalDateTime?,
    val updatedAt: LocalDateTime?,
)