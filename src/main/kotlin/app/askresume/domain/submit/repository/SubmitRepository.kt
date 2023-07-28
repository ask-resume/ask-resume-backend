package app.askresume.domain.submit.repository

import app.askresume.domain.member.model.Member
import app.askresume.domain.submit.exception.SubmitNotFoundException
import app.askresume.domain.submit.model.Submit
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.findByIdOrNull

fun SubmitRepository.findSubmitById(id: Long): Submit = findByIdOrNull(id)
    ?: throw SubmitNotFoundException(id)

fun SubmitRepository.existsSubmitByIdAndMember(id: Long, member: Member) =
    check(existsByIdAndMember(id, member)) {
        "에러 메세지 연구해야함"
    }

interface SubmitRepository : JpaRepository<Submit, Long> {

    fun existsByIdAndMember(id: Long, member: Member): Boolean

}

