package app.askresume.domain.submit.repository

import app.askresume.domain.submit.exception.SubmitDataNotFoundException
import app.askresume.domain.submit.model.SubmitData
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.findByIdOrNull

fun SubmitDataRepository.findSubmitDataById(id: Long): SubmitData =
    findByIdOrNull(id) ?: throw SubmitDataNotFoundException(id)


interface SubmitDataRepository : JpaRepository<SubmitData, Long> {
}
