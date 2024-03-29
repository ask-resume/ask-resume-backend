package app.askresume.domain.manager.service

import app.askresume.global.util.LoggerUtil.logger
import org.apache.pdfbox.pdmodel.PDDocument
import org.apache.pdfbox.text.PDFTextStripper
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile


@Service
class PdfManagerService {

    val log = logger()

    fun pdfToText(file: MultipartFile): String {

        val modifiedText = StringBuilder()

        file.inputStream.use { inputStream ->
            val document = PDDocument.load(inputStream)
            val pdfTextStripper = PDFTextStripper()

            val lines = pdfTextStripper.getText(document).lines()
            for (line in lines) {
                modifiedText.append(replaceUnicodeWithSpace(line))
                modifiedText.append("\n") // 띄어쓰기 추가
            }
        }

        return modifiedText.toString()
    }

    private fun replaceUnicodeWithSpace(input: String): String {
        val output = StringBuilder()
        for (c in input) {
            if ((c in '\u0000'..'\u0020')) {
                output.append(' ')
            } else {
                output.append(c)
            }
        }
        return output.toString()
    }
}

