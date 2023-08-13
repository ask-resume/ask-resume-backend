package app.askresume.domain.common

import org.hibernate.annotations.*
import org.springframework.data.annotation.CreatedBy
import org.springframework.data.annotation.LastModifiedBy
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.ZonedDateTime
import javax.persistence.*

@MappedSuperclass
@EntityListeners(value = [AuditingEntityListener::class])
abstract class BaseEntity : BaseTimeEntity() {

    @Comment("등록자")
    @CreatedBy
    @Column(updatable = false)
    var createdBy: Long? = null

    @Comment("수정자")
    @LastModifiedBy
    @Column(insertable = false)
    var updatedBy: Long? = null
}

@DynamicInsert
@DynamicUpdate
@MappedSuperclass
abstract class BaseTimeEntity {

    @Comment(value = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    @Comment("등록일")
    @Column(updatable = false)
    lateinit var createdAt: ZonedDateTime

    @Comment("수정일")
    @Column(insertable = false)
    lateinit var updatedAt: ZonedDateTime

    @ColumnDefault("false")
    @Comment(value = "삭제유무")
    @Column(nullable = false)
    val isDeleted: Boolean = false

    @PrePersist
    fun prePersist() {
        this.createdAt = ZonedDateTime.now()
    }

    @PreUpdate
    fun preUpdate() {
        this.updatedAt = ZonedDateTime.now()
    }
}