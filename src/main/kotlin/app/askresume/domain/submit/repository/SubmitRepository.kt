package app.askresume.domain.submit.repository

import app.askresume.domain.member.model.Member
import app.askresume.domain.submit.exception.SubmitNotFoundException
import app.askresume.domain.submit.exception.UnauthorizedSubmitAccessException
import app.askresume.domain.submit.model.Submit
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.findByIdOrNull

fun SubmitRepository.findSubmitById(id: Long): Submit = findByIdOrNull(id)
    ?: throw SubmitNotFoundException(id)

fun SubmitRepository.existsSubmitByIdAndMember(id: Long, member: Member) =
    if(existsByIdAndMember(id, member)) null
    else throw UnauthorizedSubmitAccessException(id)

interface SubmitRepository : JpaRepository<Submit, Long> {

    fun existsByIdAndMember(id: Long, member: Member): Boolean

}

