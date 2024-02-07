package app.askresume.api.extract.controller

import app.askresume.api.extract.usecase.ExtractUseCase
import app.askresume.api.extract.vo.ExtractedTextResponse
import app.askresume.domain.manager.validator.PdfManagerValidator
import app.askresume.global.model.ApiResult
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@Tag(name = "extract", description = "특정 Input을 text로 추출하는 API")
@Validated
@RestController
@RequestMapping("/api")
class ExtractController(
    private val extractUseCase: ExtractUseCase,
    private val pdfManagerValidator: PdfManagerValidator,
) {

    @Tag(name = "extract")
    @Operation(summary = "이력서를 Text로 전환 API", description = "이력서를 Text로 전환 API")
    @PostMapping(value = ["/v1/extract/pdf"], consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    fun extractTextFromPdf(
        @Parameter(name = "resume", description = "이력서PDF파일, maxSize: 3MB, 확장자: pdf)", required = true)
        @RequestPart("resume")
        file: MultipartFile
    ): ResponseEntity<ApiResult<ExtractedTextResponse>> {
        pdfManagerValidator.validateContentType(file.contentType)
        return ResponseEntity.ok(ApiResult(extractUseCase.pdfToText(file)))
    }

}
