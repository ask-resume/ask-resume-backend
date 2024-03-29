package app.askresume.domain.job.model

import app.askresume.domain.BaseEntity
import app.askresume.domain.locale.constant.LocaleType
import org.hibernate.annotations.Comment
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.Where
import javax.persistence.*

@Where(clause = "is_deleted = false")
@SQLDelete(sql = "UPDATE job SET is_deleted = true WHERE id = ?")
@Entity
class Job(
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "job_master_id", foreignKey = ForeignKey(ConstraintMode.NO_CONSTRAINT))
    @Comment(value = "직업마스터 ID")
    val jobMaster: JobMaster,

    name: String,
    locale: LocaleType,

    @Comment(value = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
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