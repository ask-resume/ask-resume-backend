package app.askresume.domain.generative.interview.repository

import app.askresume.domain.generative.interview.model.ResultInterviewMaker
import org.springframework.data.jpa.repository.JpaRepository

interface InterviewMakerRepository : JpaRepository<ResultInterviewMaker, Long>