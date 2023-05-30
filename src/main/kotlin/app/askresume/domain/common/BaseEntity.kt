package app.askresume.domain.common

import org.springframework.data.annotation.CreatedBy
import org.springframework.data.annotation.LastModifiedBy
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import javax.persistence.Column
import javax.persistence.EntityListeners
import javax.persistence.MappedSuperclass

@EntityListeners(value = [AuditingEntityListener::class])
@MappedSuperclass
abstract class BaseEntity : BaseTimeEntity() {
    @CreatedBy
    @Column(updatable = false)
    val createdBy: String? = null

    @LastModifiedBy
    val updatedBy: String? = null
}

