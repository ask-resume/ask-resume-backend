package app.askresume.api.extract.controller

import app.askresume.api.extract.dto.response.ExtractedTextResponse
import app.askresume.api.extract.service.ExtractService
import app.askresume.api.extract.validator.ExtractValidator
import app.askresume.global.model.ApiResult
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.hibernate.validator.constraints.URL
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import springfox.documentation.annotations.ApiIgnore
import javax.validation.constraints.NotBlank

@Tag(name = "extract", description = "특정 Input을 text로 추출하는 API")
@Validated
@RestController
@RequestMapping("/api")
class ExtractController(
    private val extractService: ExtractService,
    private val extractValidator: ExtractValidator,
) {

    @Tag(name = "extract")
    @Operation(summary = "이력서를 Text로 전환 API", description = "이력서를 Text로 전환 API")
    @ApiResponses(
        ApiResponse(responseCode = "500", description = "서버 오류 발생(관리자 문의)"),
        ApiResponse(responseCode = "500", description = "(FILE-002) 텍스트 추출 중 에러 발생."),
        ApiResponse(responseCode = "400", description = "(FILE-001) 허용되지 않은 CONTENT TYPE.")
    )
    @PostMapping(value = ["/v1/extract/pdf"], consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    fun extractTextFromPdf(
        @Parameter(
            name = "resume",
            description = "이력서PDF파일, maxSize: 3MB, 확장자: pdf)",
            required = true
        )
        @RequestPart("resume")
        file: MultipartFile
    ): ResponseEntity<ApiResult<ExtractedTextResponse>> {
        extractValidator.validateContentType(file.contentType)
        return ResponseEntity.ok(ApiResult(extractService.pdfToText(file)))
    }

    @ApiIgnore(value = "가상DOM 등을 사용하는 사이트에서 동작 이슈가 있어, 수정전까지 Ignore")
    @Tag(name = "extract")
    @Operation(summary = "Link를 Text로 전환하는 API", description = "Link를 Text로 전환하는 API")
    @ApiResponses(
        ApiResponse(responseCode = "500", description = "서버 오류 발생(관리자 문의)"),
        ApiResponse(responseCode = "500", description = "(FILE-002) 텍스트 추출 중 에러 발생.")
    )
    @GetMapping("/v1/extract/link")
    fun scrapeWebPage(
        @Parameter(
            name = "url",
            description = "이력서 URL",
            required = true
        )
        @URL(message = "올바른 URL 형식이 아닙니다.")
        @RequestParam
        @NotBlank
        url: String
    ): ResponseEntity<ApiResult<ExtractedTextResponse>> {
        return ResponseEntity.ok(ApiResult(extractService.linkToText(url)))
    }
}
