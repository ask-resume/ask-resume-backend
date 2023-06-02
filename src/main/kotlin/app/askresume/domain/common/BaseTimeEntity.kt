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
    @Comment(value = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    @Comment("등록일")
    @CreatedDate
    @Column(updatable = false)
    lateinit var createdAt: LocalDateTime

    @Comment("수정일")
    @LastModifiedDate
    @Column(insertable = false)
    lateinit var updatedAt: LocalDateTime

    @ColumnDefault("'Y'")
    @Comment(value = "삭제유무")
    @Column(length = 1, nullable = false)
    val deletedAt: String = "Y"
}

