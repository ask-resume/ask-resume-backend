package app.askresume.domain.extract.service

import app.askresume.api.extract.vo.ExtractedTextResponse
import app.askresume.global.util.LoggerUtil.logger
import org.apache.pdfbox.pdmodel.PDDocument
import org.apache.pdfbox.text.PDFTextStripper
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile


@Service
class ExtractService {

    val log = logger()

    fun pdfToText(file: MultipartFile): ExtractedTextResponse {
        var text: String
        file.inputStream.use { inputStream ->
            val document = PDDocument.load(inputStream)
            val pdfTextStripper = PDFTextStripper()

            text = pdfTextStripper.getText(document)
            document.close()
        }

        return ExtractedTextResponse(text)
    }

}

