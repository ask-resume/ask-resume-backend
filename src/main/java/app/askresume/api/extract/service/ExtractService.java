package app.askresume.api.extract.service;

import app.askresume.api.extract.dto.response.ExtractedTextResponse;
import app.askresume.global.error.ErrorCode;
import app.askresume.global.error.exception.BusinessException;
import app.askresume.global.util.ParsingUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@Slf4j
@Service
@RequiredArgsConstructor
public class ExtractService {

    public ExtractedTextResponse pdfToText(MultipartFile file) {
        String text = "";

        try (InputStream inputStream = file.getInputStream()) {
            PDDocument document = PDDocument.load(inputStream);
            PDFTextStripper pdfTextStripper = new PDFTextStripper();

            text = ParsingUtils.removeEmojis(pdfTextStripper.getText(document));
            text = ParsingUtils.removeUrls(text);

            log.debug(text);
            document.close();
        } catch (IOException e) {
            log.error("PDF파일을 TEXT로 저장하는 도중 ERROR 발생 \n에러 내용 : {}", e.getMessage());
            throw new BusinessException(ErrorCode.TEXT_EXTRACTION_FAILED);
        }

        return ExtractedTextResponse.builder()
                .text(text)
                .build();
    }

    public ExtractedTextResponse linkToText(String url) {

        String text = "";

        try {
            Document doc = Jsoup.connect(url).get();
            Elements elements = doc.getAllElements();

            String body = elements.toString().replaceAll("(?is)<body.*?>\\s*(.*?)\\s*</body>", "$1");
            text = body.replaceAll("<.*?>", "").replaceAll("(?m)^\\s*$[\n\r]{1,}", "");

        } catch (IOException e) {
            log.error("Elements를 찾아, TEXT로 저장하는 도중 ERROR 발생 \n에러 내용 : {}", e.getMessage());
            throw new BusinessException(ErrorCode.TEXT_EXTRACTION_FAILED);
        }

        return ExtractedTextResponse.builder()
                .text(text)
                .build();
    }
}
