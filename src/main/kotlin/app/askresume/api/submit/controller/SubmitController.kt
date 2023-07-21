package app.askresume.api.submit.controller

import app.askresume.api.submit.dto.SubmitResponse
import app.askresume.api.submit.facade.SubmitFacade
import app.askresume.global.model.ApiResult
import app.askresume.global.model.PageResult
import app.askresume.global.resolver.memberinfo.MemberInfo
import app.askresume.global.resolver.memberinfo.MemberInfoDto
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springdoc.api.annotations.ParameterObject
import org.springframework.data.domain.Pageable
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "submit", description = "제출 정보 API")
@RestController
@RequestMapping("/api")
class SubmitController(
    private val submitFacade: SubmitFacade,
) {

    @Tag(name = "submit")
    @Operation(summary = "내 제출 정보 목록 조회 API", description = "유저가 자신이 제출한 생성형 AI 서비스 요청 목록을 페이징 처리된 상태로 조회합니다. 본인이 아니면 조회할 수 없습니다.")
    @GetMapping("/my-member/submit") // TODO : 개발용으로 WebConfig에 임시로 오픈해뒀습니다. 기능 개발 완료 시 꼭 원상복구해주세요.
    fun findMySubmits(
        @ParameterObject pageable: Pageable,
        @MemberInfo memberInfoDto: MemberInfoDto,
    ): ResponseEntity<ApiResult<PageResult<SubmitResponse>>> {
        val mySubmits = submitFacade.findMySubmits(pageable, memberInfoDto)

        return ResponseEntity.ok(ApiResult(mySubmits))
    }

}