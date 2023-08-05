package app.askresume.domain.submit.repository

import app.askresume.domain.member.model.QMember.member
import app.askresume.domain.submit.constant.SubmitDataStatus
import app.askresume.domain.submit.constant.SubmitStatus
import app.askresume.domain.submit.dto.FirstSubmittedDto
import app.askresume.domain.submit.dto.QFirstSubmittedDto
import app.askresume.domain.submit.dto.QSubmitDto
import app.askresume.domain.submit.dto.SubmitDto
import app.askresume.domain.submit.model.QSubmit.submit
import app.askresume.domain.submit.model.QSubmitData.submitData
import app.askresume.domain.submit.repository.expression.SubmitExpression
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Repository

@Repository
class SubmitQueryRepository(
    private val queryFactory: JPAQueryFactory,
) {
    fun findQueryRequestedFirstSubmit(): FirstSubmittedDto? {
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

    fun findQuerySubmitList(memberId: Long?, pageable: Pageable): Page<SubmitDto> {

        val totalCount = findCountSubmitList(memberId)
        val indexes = findIndexSubmitList(memberId, pageable)

        val fetch = queryFactory
            .select(
                QSubmitDto(
                    member.id,
                    member.email,
                    member.memberType,
                    submit.id,
                    submit.title,
                    submit.dataCount,
                    submit.attempts,
                    submit.submitStatus,
                    submit.serviceType,
                    submit.createdAt,
                    submit.updatedAt,
                )
            )
            .from(submit)
            .leftJoin(member)
            .on(
                member.id.eq(submit.member.id)
            )
            .where(
                submit.id.`in`(indexes)
            )
            .orderBy(submit.id.desc())
            .fetch().toList()

        return PageImpl(fetch, pageable, totalCount)
    }

    private fun findCountSubmitList(memberId: Long?): Long {
        return queryFactory
            .select(submit.id.count())
            .from(submit)
            .where(
                SubmitExpression.memberIdEq(memberId)
            )
            .fetchOne() ?: 0
    }

    private fun findIndexSubmitList(memberId: Long?, pageable: Pageable): List<Long> {
        return queryFactory
            .select(submit.id)
            .from(submit)
            .where(
                SubmitExpression.memberIdEq(memberId)
            )
            .orderBy(submit.id.desc())
            .limit(pageable.pageSize.toLong())
            .offset(pageable.offset)
            .fetch()
    }


}