package app.askresume.api.member.controller

import app.askresume.api.member.facade.MyMemberFacade
import app.askresume.api.member.vo.MemberInfoResponse
import app.askresume.api.member.vo.ModifyInfoRequest
import app.askresume.global.model.ApiResult
import app.askresume.global.resolver.memberinfo.MemberInfo
import app.askresume.global.resolver.memberinfo.MemberInfoResolver
import app.askresume.global.util.UriUtil
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*


@Tag(name = "my-member", description = "내정보 API")
@RestController
@RequestMapping("/api/my-member")
class MyMemberController(
    private val myMemberFacade: MyMemberFacade,
) {

    @Tag(name = "my-member")
    @Operation(summary = "내 정보 조회 API", description = "내 정보 조회 API")
    @GetMapping
    fun findMyInfo(
        @MemberInfoResolver memberInfo: MemberInfo
    ): ResponseEntity<ApiResult<MemberInfoResponse>> {
        val memberId: Long = memberInfo.memberId
        return ResponseEntity.ok(ApiResult(myMemberFacade.findMyMemberInfo(memberId)))
    }

    @Tag(name = "my-member")
    @Operation(summary = "내 정보 변경 API", description = "내 정보 변경 API")
    @PutMapping
    fun modify(
        @Validated @RequestBody request: ModifyInfoRequest,
        @MemberInfoResolver memberInfo: MemberInfo
    ): ResponseEntity<Void> {

        val memberId: Long = memberInfo.memberId
        myMemberFacade.modifyMyMemberInfo(memberId, request)

        return ResponseEntity.created(UriUtil.createUri()).build()
    }

    @Tag(name = "my-member")
    @Operation(summary = "회원 탈퇴 API", description = "회원 탈퇴 API")
    @DeleteMapping
    fun secession(
        @MemberInfoResolver memberInfo: MemberInfo
    ): ResponseEntity<Void> {
        val memberId: Long = memberInfo.memberId
        myMemberFacade.secessionMember(memberId)

        return ResponseEntity.noContent().build()
    }
}

