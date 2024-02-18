package app.askresume.api.job.controller

import app.askresume.api.job.usecase.AdminJobUseCase
import app.askresume.api.job.vo.SaveJobRequest
import app.askresume.global.util.LoggerUtil.logger
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@Tag(name = "admin job", description = "직업데이터 API (어드민)")
@RestController
@RequestMapping("/api/admin/jobs")
class AdminJobController(
    private val adminJobUseCase: AdminJobUseCase,
) {

    val log = logger()

    @Tag(name = "admin job")
    @Operation(summary = "직업 정보를 등록하는 API", description = "직업 정보를 등록하는 API")
    @PostMapping
    fun saveJobs(
        @RequestBody @Valid request: SaveJobRequest,
    ) = adminJobUseCase.save(request)
}