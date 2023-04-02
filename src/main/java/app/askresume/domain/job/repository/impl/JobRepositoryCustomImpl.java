package app.askresume.domain.job.repository.impl;

import app.askresume.api.job.dto.response.JobResponse;
import app.askresume.api.job.dto.response.QJobResponse;
import app.askresume.domain.job.repository.JobRepositoryCustom;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static app.askresume.domain.job.model.QJob.job;
import static app.askresume.domain.job.model.QJobMaster.jobMaster;

@Repository
@RequiredArgsConstructor
public class JobRepositoryCustomImpl implements JobRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<JobResponse> findJobs(String locale) {
        return queryFactory
                .select(new QJobResponse(jobMaster.id, job.name))
                .from(jobMaster)
                .leftJoin(job)
                .on(jobMaster.id.eq(job.jobMaster.id))
                .where(job.locale.eq(locale))
                .fetch();
    }
}
