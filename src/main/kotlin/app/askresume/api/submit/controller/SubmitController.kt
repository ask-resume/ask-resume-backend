package app.askresume.api.submit.controller

import app.askresume.api.submit.usecase.SubmitUseCase
import app.askresume.api.submit.vo.SubmitDetailResponse
import app.askresume.api.submit.vo.SubmitResponse
import app.askresume.global.model.ApiResult
import app.askresume.global.model.PageResponse
import app.askresume.global.resolver.memberinfo.MemberInfo
import app.askresume.global.resolver.memberinfo.MemberInfoResolver
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springdoc.api.annotations.ParameterObject
import org.springframework.data.domain.Pageable
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "submit", description = "제출 정보 API")
@RestController
@RequestMapping("/api/submits")
class SubmitController(
    private val useCase: SubmitUseCase,
) {

    @Tag(name = "submit")
    @Operation(
        summary = "내 제출 정보 목록 조회 API",
        description = """
            이 API는 사용자가 제출한 생성형 AI 서비스 요청 목록을 페이징 처리하여 조회하는 데 사용됩니다.
            
            유저는 페이지 번호와 페이지당 결과 수를 지정하여 원하는 범위의 데이터를 가져올 수 있습니다.
        """
    )
    @GetMapping
    fun findMySubmits(
        @ParameterObject pageable: Pageable,
        @MemberInfoResolver memberInfo: MemberInfo,
    ): ApiResult<PageResponse<SubmitResponse>> = ApiResult(useCase.findMySubmits(pageable, memberInfo.memberId))

    @Tag(name = "submit")
    @Operation(
        summary = "생성형 AI 서비스 생성 결과 상세조회",
        description = """
            이 API는 생성형 AI 서비스의 결과를 상세 조회하는 데 사용됩니다. 
            사용자는 서비스 식별자(submitId)를 입력하여 해당 서비스가 생성한 데이터를 가져올 수 있습니다. 
            serviceType에 따라, 다른 response을 전달합니다.
        """
    )
    @GetMapping("/{submitId}")
    fun findMySubmitDetail(
        @PathVariable submitId: Long,
        @MemberInfoResolver memberInfo: MemberInfo,
    ): ApiResult<SubmitDetailResponse> =
        ApiResult(
            useCase.findMySubmitsDetail(
                submitId = submitId,
                memberId = memberInfo.memberId,
            )
        )

}