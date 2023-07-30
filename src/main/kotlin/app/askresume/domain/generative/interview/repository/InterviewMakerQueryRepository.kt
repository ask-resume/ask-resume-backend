package app.askresume.domain.generative.interview.repository

import app.askresume.domain.generative.interview.dto.InterviewMakerDto
import app.askresume.domain.generative.interview.dto.QInterviewMakerDto
import app.askresume.domain.generative.interview.model.QResultInterviewMaker.resultInterviewMaker
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Repository

@Repository
class InterviewMakerQueryRepository(
    private val queryFactory: JPAQueryFactory,
) {

    fun findQueryInterviewMaker(submitId: Long): List<InterviewMakerDto> {
        return queryFactory
            .select(
                QInterviewMakerDto(
                    resultInterviewMaker.id,
                    resultInterviewMaker.question,
                    resultInterviewMaker.bestAnswer,
                    resultInterviewMaker.satisfaction,
                )
            )
            .from(resultInterviewMaker)
            .where(
                resultInterviewMaker.submit.id.eq(submitId)
            )
            .fetch()
    }


}