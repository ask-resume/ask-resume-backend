package app.askresume.api.extract.controller
//
//import app.askresume.api.extract.usecase.ExtractUseCase
//import app.askresume.api.extract.vo.ExtractedTextResponse
//import app.askresume.domain.manager.validator.PdfManagerValidator
//import app.askresume.domain.member.constant.Role
//import app.askresume.global.cookie.CookieProvider
//import app.askresume.global.jwt.service.TokenManager
//import com.fasterxml.jackson.databind.ObjectMapper
//import org.junit.jupiter.api.Test
//import org.mockito.ArgumentMatchers.any
//import org.mockito.BDDMockito.given
//import org.springframework.beans.factory.annotation.Autowired
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
//import org.springframework.boot.test.mock.mockito.MockBean
//import org.springframework.http.MediaType
//import org.springframework.mock.web.MockMultipartFile
//import org.springframework.test.context.ActiveProfiles
//import org.springframework.test.web.servlet.MockMvc
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart
//import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
//import javax.servlet.http.Cookie
//
//
//@ActiveProfiles("test")
//@WebMvcTest(controllers = [ExtractController::class])
////@SpringBootTest
////@AutoConfigureMockMvc
//class ExtractControllerTest {
//
//    @Autowired
//    private lateinit var mockMvc : MockMvc
//
//    @Autowired
//    private lateinit var objectMapper: ObjectMapper
//
//    @MockBean
//    private lateinit var extractUseCase: ExtractUseCase
//
//    @MockBean
//    private lateinit var pdfManagerValidator: PdfManagerValidator
//
//    @MockBean
//    private lateinit var tokenManagerMock : TokenManager
//
//    @Autowired
//    private lateinit var tokenManager : TokenManager
//
//    @MockBean
//    private lateinit var cookieProvider: CookieProvider
//
//    @Test
//    fun extractTextFromPdfTest() {
//
//        // given
//        val file = MockMultipartFile("resume", "resume.pdf", MediaType.APPLICATION_PDF_VALUE, "PDF content".toByteArray())
//        val expectedResponse = ExtractedTextResponse("PDF content")
//        //given(pdfManagerValidator.validateContentType(file.contentType))
//        given(extractUseCase.pdfToText(file)).willReturn(expectedResponse)
//
//        // when & then
//        mockMvc.perform(
//            multipart("/api/v1/extract/pdf").file(file)
//                .header("access_token",token)
//                .contentType(MediaType.MULTIPART_FORM_DATA)
//        )
//            .andExpect(status().isOk())
//            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
//            .andExpect(jsonPath("$.data.text").value("PDF content"))
//    }
//
//}