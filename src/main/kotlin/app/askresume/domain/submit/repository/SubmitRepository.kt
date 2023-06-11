package app.askresume.domain.submit.repository

import app.askresume.domain.submit.model.Submit
import org.springframework.data.jpa.repository.JpaRepository

interface SubmitRepository : JpaRepository<Submit, Long> {
}

