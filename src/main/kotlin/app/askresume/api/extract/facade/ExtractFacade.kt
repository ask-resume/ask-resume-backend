package app.askresume.api.extract.facade

import app.askresume.api.extract.vo.ExtractedTextResponse
import app.askresume.domain.extract.service.ExtractService
import app.askresume.global.annotation.Facade
import app.askresume.global.util.LoggerUtil.logger
import org.springframework.web.multipart.MultipartFile

@Facade
class ExtractFacade(
    private val extractService: ExtractService
) {

    val log = logger()

    fun pdfToText(file: MultipartFile): ExtractedTextResponse {
        return extractService.pdfToText(file)
    }
}