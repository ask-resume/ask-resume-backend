package app.askresume.domain.job.model

import app.askresume.domain.common.BaseEntity
import app.askresume.domain.locale.constant.LocaleType
import org.hibernate.annotations.*
import javax.persistence.*
import javax.persistence.Entity

@SQLDelete(sql = "UPDATE job SET is_deleted = 'N' WHERE id = ?")
@Entity
class Job(
    jobMaster: JobMaster,
    name: String,
    locale: LocaleType,
) : BaseEntity() {

    @ManyToOne
    @Comment(value = "직업마스터 ID")
    @JoinColumn(nullable = false)
    var jobMaster: JobMaster = jobMaster
        protected set

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