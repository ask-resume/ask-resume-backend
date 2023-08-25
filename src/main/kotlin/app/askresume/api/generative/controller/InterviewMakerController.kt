package app.askresume.api.generative.controller

import app.askresume.api.generative.facade.InterviewMakerFacade
import app.askresume.api.generative.vo.InformationRequest
import app.askresume.api.generative.vo.InterviewMakerRequest
import app.askresume.domain.manager.validator.PdfManagerValidator
import app.askresume.global.resolver.memberinfo.MemberInfo
import app.askresume.global.resolver.memberinfo.MemberInfoResolver
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@Tag(name = "generative", description = "생성, 스케줄 대기열에 등록 API")
@RestController
@RequestMapping("/api/generative/interview-maker")
class InterviewMakerController(
    private val interviewMakerFacade: InterviewMakerFacade,
    private val pdfManagerValidator: PdfManagerValidator,
) {

    @Tag(name = "generative")
    @Operation(
        summary = "[interview-maker] 수기 입력 제출, 예상 질문, 모범 답안 대기열에 등록 API",
    )
    @PostMapping("/manual")
    fun saveManualSubmit(
        @Validated @RequestBody request: InterviewMakerRequest,
        @MemberInfoResolver memberInfo: MemberInfo,
    ): ResponseEntity<Void> {
        interviewMakerFacade.saveManualSubmit(request, memberInfo)

        return ResponseEntity.status(HttpStatus.CREATED).build()
    }

    @Tag(name = "generative")
    @Operation(
        summary = "[interview-maker] PDF 제출, 예상 질문, 모범 답안 대기열에 등록 API",
    )
    @PostMapping(value = ["/pdf"], consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    fun savePdfSubmit(
        @Validated request: InformationRequest,

        @Parameter(name = "resume", description = "이력서PDF파일, maxSize: 3MB, 확장자: pdf)", required = true)
        @RequestPart("resume") file: MultipartFile,
        @MemberInfoResolver memberInfo: MemberInfo,
    ): ResponseEntity<Void> {
        pdfManagerValidator.validateContentType(file.contentType)
        interviewMakerFacade.savePdfSubmit(request, file, memberInfo)

        return ResponseEntity.status(HttpStatus.CREATED).build()
    }

}