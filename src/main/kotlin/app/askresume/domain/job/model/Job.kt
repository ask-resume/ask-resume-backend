package app.askresume.domain.job.model

import app.askresume.domain.common.BaseTimeEntity
import app.askresume.domain.locale.constant.LocaleType
import org.hibernate.annotations.*
import javax.persistence.*
import javax.persistence.Entity

@SQLDelete(sql = "UPDATE job SET deleted_at = 'N' WHERE id = ?")
@Entity
class Job(

    @ManyToOne
    @Comment(value = "직업마스터 ID")
    @JoinColumn(name = "jobMasterId", nullable = false)
    val jobMaster: JobMaster,

    @Comment(value = "직업명")
    @Column(length = 150, nullable = false)
    val name: String,

    @Comment(value = "언어")
    @Column(length = 5, nullable = false)
    @Enumerated(EnumType.STRING)
    val locale: LocaleType,

    ) : BaseTimeEntity()