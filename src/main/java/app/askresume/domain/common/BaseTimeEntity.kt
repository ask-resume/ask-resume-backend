package app.askresume.domain.common

import org.hibernate.annotations.Comment
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime
import javax.persistence.*

@EntityListeners(value = [AuditingEntityListener::class])
@MappedSuperclass
abstract class BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment(value = "id")
    private val id: Long? = null

    @Comment("등록일")
    @CreatedDate
    @Column(updatable = false)
    private val createdAt: LocalDateTime? = null

    @Comment("수정일")
    @LastModifiedDate
    private val updatedAt: LocalDateTime? = null
}

