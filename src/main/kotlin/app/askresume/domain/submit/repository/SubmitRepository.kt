package app.askresume.domain.submit.repository

import app.askresume.domain.submit.exception.SubmitNotFoundException
import app.askresume.domain.submit.model.Submit
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.findByIdOrNull

fun SubmitRepository.findSubmitById(id: Long): Submit = findByIdOrNull(id)
    ?: throw SubmitNotFoundException(id)

interface SubmitRepository : JpaRepository<Submit, Long> {
}

