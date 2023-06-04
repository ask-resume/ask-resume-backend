package app.askresume.domain.common

import org.hibernate.annotations.Comment
import org.springframework.data.annotation.CreatedBy
import org.springframework.data.annotation.LastModifiedBy
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import javax.persistence.Column
import javax.persistence.EntityListeners
import javax.persistence.MappedSuperclass

@EntityListeners(value = [AuditingEntityListener::class])
@MappedSuperclass
abstract class BaseEntity : BaseTimeEntity() {

    @Comment("등록자")
    @CreatedBy
    @Column(updatable = false)
    val createdBy: String? = null

    @Comment("수정자")
    @LastModifiedBy
    @Column(insertable = false)
    val updatedBy: String? = null
}

