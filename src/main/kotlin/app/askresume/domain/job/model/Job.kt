package app.askresume.domain.job.model

import app.askresume.domain.common.BaseEntity
import app.askresume.domain.locale.constant.LocaleType
import org.hibernate.annotations.*
import javax.persistence.*
import javax.persistence.Entity

@SQLDelete(sql = "UPDATE job SET is_deleted = 'N' WHERE id = ?")
@Entity
class Job(
    @ManyToOne
    @Comment(value = "직업마스터 ID")
    val jobMaster: JobMaster,
    name: String,
    locale: LocaleType,
) : BaseEntity() {

    @Comment(value = "직업명")
    @Column(length = 150, nullable = false)
    var name: String = name
        protected set

    @Comment(value = "언어")
    @Column(length = 5, nullable = false)
    @Enumerated(EnumType.STRING)
    var locale: LocaleType = locale
        protected set

}