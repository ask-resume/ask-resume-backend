package app.askresume.api.member.controller

import app.askresume.api.ApiResult
import app.askresume.api.member.usecase.MyMemberUseCase
import app.askresume.api.member.vo.MemberInfoResponse
import app.askresume.api.member.vo.ModifyInfoRequest
import app.askresume.global.resolver.memberinfo.MemberInfo
import app.askresume.global.resolver.memberinfo.MemberInfoResolver
import app.askresume.global.util.UriUtil
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import javax.validation.Valid


@Tag(name = "my-member", description = "내정보 API")
@RestController
@RequestMapping("/api/my-member")
class MyMemberController(
    private val myMemberUseCase: MyMemberUseCase,
) {

    @Tag(name = "my-member")
    @Operation(summary = "내 정보 조회 API", description = "내 정보 조회 API")
    @GetMapping
    fun findMyInfo(
        @MemberInfoResolver memberInfo: MemberInfo
    ): ApiResult<MemberInfoResponse> = ApiResult(myMemberUseCase.findMyMemberInfo(memberInfo.memberId))

    @Tag(name = "my-member")
    @Operation(summary = "내 정보 변경 API", description = "내 정보 변경 API")
    @PutMapping
    fun modify(
        @Valid @RequestBody request: ModifyInfoRequest,
        @MemberInfoResolver memberInfo: MemberInfo
    ): ResponseEntity<Void> {
        myMemberUseCase.modifyMyMemberInfo(memberInfo.memberId, request)

        return ResponseEntity.created(UriUtil.createUri()).build()
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Tag(name = "my-member")
    @Operation(summary = "회원 탈퇴 API", description = "회원 탈퇴 API")
    @DeleteMapping
    fun secession(
        @MemberInfoResolver memberInfo: MemberInfo
    ) = myMemberUseCase.secessionMember(memberInfo.memberId)
}

