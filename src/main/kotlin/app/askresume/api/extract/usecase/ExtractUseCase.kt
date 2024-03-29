package app.askresume.api.extract.usecase

import app.askresume.api.extract.vo.ExtractedTextResponse
import app.askresume.domain.manager.service.PdfManagerService
import app.askresume.global.annotation.UseCase
import app.askresume.global.util.LoggerUtil.logger
import org.springframework.web.multipart.MultipartFile

@UseCase
class ExtractUseCase(
    private val pdfManagerService: PdfManagerService
) {

    val log = logger()

    fun pdfToText(file: MultipartFile): ExtractedTextResponse {
        return ExtractedTextResponse(
            pdfManagerService.pdfToText(file)
        )
    }
}