package app.askresume.domain.submit.repository

import app.askresume.domain.submit.model.SubmitData
import org.springframework.data.jpa.repository.JpaRepository

interface SubmitDataRepository : JpaRepository<SubmitData, Long> {
}
