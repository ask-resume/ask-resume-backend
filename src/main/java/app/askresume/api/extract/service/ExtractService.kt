package app.askresume.api.extract.service

import app.askresume.api.extract.dto.response.ExtractedTextResponse
import app.askresume.global.error.ErrorCode
import app.askresume.global.error.exception.BusinessException
import app.askresume.global.util.LoggerUtil.log
import app.askresume.global.util.ParsingUtils
import org.apache.pdfbox.pdmodel.PDDocument
import org.apache.pdfbox.text.PDFTextStripper
import org.jsoup.Jsoup
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.IOException


@Service
class ExtractService {

    val log = log()

    fun pdfToText(file: MultipartFile): ExtractedTextResponse {
        var text = ""
        try {
            file.inputStream.use { inputStream ->
                val document = PDDocument.load(inputStream)
                val pdfTextStripper = PDFTextStripper()

                text = ParsingUtils.removeEmojis(pdfTextStripper.getText(document))
                text = ParsingUtils.removeUrls(text)

                log.debug(text)
                document.close()
            }
        } catch (e: IOException) {
            log.error("PDF파일을 TEXT로 저장하는 도중 ERROR 발생 \n에러 내용 : {}", e.message)
            throw BusinessException(ErrorCode.TEXT_EXTRACTION_FAILED)
        }

        return ExtractedTextResponse(text)
    }

    fun linkToText(url: String?): ExtractedTextResponse {
        val text = try {
            val doc = Jsoup.connect(url!!).get()
            val elements = doc.allElements

            val body = elements.toString().replace("(?is)<body.*?>\\s*(.*?)\\s*</body>".toRegex(), "$1")

            body.replace("<.*?>".toRegex(), "").replace("(?m)^\\s*$[\n\r]{1,}".toRegex(), "")
        } catch (e: IOException) {
            log.error("Elements를 TEXT로 저장하는 도중 ERROR 발생 \n에러 내용 : {}", e.message)
            throw BusinessException(ErrorCode.TEXT_EXTRACTION_FAILED)
        }

        return ExtractedTextResponse(text)
    }
}

