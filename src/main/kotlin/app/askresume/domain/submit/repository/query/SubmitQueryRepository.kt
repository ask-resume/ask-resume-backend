package app.askresume.domain.submit.repository.query

import app.askresume.domain.submit.constant.SubmitDataStatus
import app.askresume.domain.submit.constant.SubmitStatus
import app.askresume.domain.submit.dto.FirstSubmittedDto
import app.askresume.domain.submit.dto.QFirstSubmittedDto
import app.askresume.domain.submit.model.QSubmit.submit
import app.askresume.domain.submit.model.QSubmitData.submitData
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Repository

@Repository
class SubmitQueryRepository(
    private val queryFactory: JPAQueryFactory
) {
    fun findRequestedFirstSubmit(): FirstSubmittedDto? {
        return queryFactory
            .select(
                QFirstSubmittedDto(
                    submit.id,
                    submitData.id,
                    submit.serviceType,
                    submitData.parameter
                )
            )
            .from(submitData)
            .leftJoin(submit)
            .on(submitData.submit.id.eq(submit.id))
            .where(
                submit.submitStatus.ne(SubmitStatus.COMPLETED),
                submit.submitStatus.ne(SubmitStatus.FAIL),
                submitData.submitDataStatus.ne(SubmitDataStatus.SUCCESS)
            )
            .orderBy(
                submit.id.asc(),
                submitData.id.asc()
            )
            .limit(1)
            .fetchOne()
    }


}