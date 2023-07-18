package app.askresume.domain.result.repository;

import app.askresume.domain.result.model.Result
import org.springframework.data.jpa.repository.JpaRepository

interface ResultRepository : JpaRepository<Result, Long>