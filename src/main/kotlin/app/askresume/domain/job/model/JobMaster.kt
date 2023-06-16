package app.askresume.domain.job.model

import app.askresume.domain.common.BaseEntity
import org.hibernate.annotations.Comment
import org.hibernate.annotations.SQLDelete
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.OneToMany


@SQLDelete(sql = "UPDATE job_master SET is_deleted = 'N' WHERE id = ?")
@Entity
class JobMaster(

    @Comment(value = "작업마스터명")
    @Column(length = 150, nullable = false)
    val masterName: String,

    ) : BaseEntity() {

    @OneToMany(mappedBy = "jobMaster", fetch = FetchType.LAZY)
    val jobs: List<Job> = ArrayList()
}

