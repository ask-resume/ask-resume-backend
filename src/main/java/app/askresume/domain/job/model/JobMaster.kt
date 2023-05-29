package app.askresume.domain.job.model

import app.askresume.domain.common.BaseTimeEntity
import org.hibernate.annotations.*
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.OneToMany


@Where(clause = "deleted_at = 'Y'")
@SQLDelete(sql = "UPDATE job_master SET deleted_at = 'N' WHERE id = ?")
@DynamicInsert
@DynamicUpdate
@Entity
class JobMaster(

    @Comment(value = "작업마스터명")
    @Column(length = 150, nullable = false)
    private val masterName: String,

    @Comment(value = "삭제유무")
    @ColumnDefault("'Y'")
    @Column(length = 1, nullable = false)
    private val deletedAt: String,

) : BaseTimeEntity() {

    @OneToMany(mappedBy = "jobMaster", fetch = FetchType.LAZY)
    val jobs: List<Job> = ArrayList()
}

