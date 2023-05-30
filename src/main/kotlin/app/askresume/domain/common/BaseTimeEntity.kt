package app.askresume.domain.common

import org.hibernate.annotations.*
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime
import javax.persistence.*

@Where(clause = "deleted_at = 'Y'")
@DynamicInsert
@DynamicUpdate
@EntityListeners(value = [AuditingEntityListener::class])
@MappedSuperclass
abstract class BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment(value = "id")
    val id: Long? = null

    @Comment("등록일")
    @CreatedDate
    @Column(updatable = false)
    val createdAt: LocalDateTime? = null

    @Comment("수정일")
    @LastModifiedDate
    val updatedAt: LocalDateTime? = null

    @ColumnDefault("'Y'")
    @Comment(value = "삭제유무")
    @Column(length = 1, nullable = false)
    val deletedAt: String = "Y"
}

