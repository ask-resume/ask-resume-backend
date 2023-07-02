package app.askresume.domain.job.repository.query

import app.askresume.domain.job.dto.JobDto
import app.askresume.domain.job.dto.QJobDto
import app.askresume.domain.job.model.QJob.job
import app.askresume.domain.job.model.QJobMaster.jobMaster
import app.askresume.domain.locale.constant.LocaleType
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Repository

@Repository
class JobDataRepositoryQuery(
    private val queryFactory: JPAQueryFactory,
) {

    fun findJobs(locale: LocaleType): List<JobDto> {
        return queryFactory
            .select(
                QJobDto(
                    jobMaster.id,
                    job.name,
                    jobMaster.createdAt,
                    jobMaster.updatedAt,
                )
            )
            .from(jobMaster)
            .leftJoin(job)
            .on(jobMaster.id.eq(job.jobMaster.id))
            .where(job.locale.eq(locale))
            .fetch()
    }
}