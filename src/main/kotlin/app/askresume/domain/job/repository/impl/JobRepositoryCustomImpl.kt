package app.askresume.domain.job.repository.impl

import app.askresume.api.job.dto.response.JobResponse
import app.askresume.api.job.dto.response.QJobResponse
import app.askresume.domain.job.model.QJob.job
import app.askresume.domain.job.model.QJobMaster.jobMaster
import app.askresume.domain.job.repository.JobRepositoryCustom
import app.askresume.domain.locale.constant.LocaleType
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Repository

@Repository
class JobRepositoryCustomImpl(
    private val queryFactory: JPAQueryFactory,
) : JobRepositoryCustom {

    override fun findJobs(locale: LocaleType): List<JobResponse> {
        return queryFactory
            .select(QJobResponse(jobMaster.id, job.name))
            .from(jobMaster)
            .leftJoin(job)
            .on(jobMaster.id.eq(job.jobMaster.id))
            .where(job.locale.eq(locale))
            .fetch()
    }
}