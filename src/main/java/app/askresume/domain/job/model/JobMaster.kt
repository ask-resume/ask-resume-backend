package app.askresume.domain.job.model

import app.askresume.domain.common.BaseTimeEntity
import org.hibernate.annotations.Comment
import org.hibernate.annotations.SQLDelete
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.OneToMany


@SQLDelete(sql = "UPDATE job_master SET deleted_at = 'N' WHERE id = ?")
@Entity
class JobMaster(

    @Comment(value = "작업마스터명")
    @Column(length = 150, nullable = false)
    val masterName: String,

    ) : BaseTimeEntity() {

    @OneToMany(mappedBy = "jobMaster", fetch = FetchType.LAZY)
    val jobs: List<Job> = ArrayList()
}

