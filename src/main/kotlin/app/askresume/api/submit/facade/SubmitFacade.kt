package app.askresume.api.submit.facade

import app.askresume.api.submit.dto.SubmitParam
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class SubmitFacade {

    val TOTAL_ELEMENTS = 1027

    fun findMySubmits(requestParams: SubmitParam.MySubmits): Map<String, Any> {
        val (page, pageSize) = requestParams
        val list = mutableListOf<Map<String, Any>>()

        val firstElement = TOTAL_ELEMENTS - (page - 1).coerceAtLeast(0) * pageSize
        val lastElement = (TOTAL_ELEMENTS - (page * pageSize) + 1).coerceAtLeast(1)

        for (i in firstElement downTo lastElement) {
            val item = mapOf(
                "id" to i,
                "service" to "INTERVIEW_MAKER",
                "title" to "[${(i + 1).toString().padStart(4, '0')}] 안녕하세요. 데이터로 일하는 개발자 황(黃) 금독수리온세상을놀라게하다입니다.",
                "status" to when (i % 3) {
                    0 -> "SUCCESS"
                    1 -> "WAITING"
                    else -> "FAILURE"
                },
                "createdAt" to "2023-06-01T21:43:27",
                "updatedAt" to "2023-06-01T21:43:27"
            )
            list.add(item)
        }

        return mapOf(
            "page" to requestParams.page,
            "pageSIze" to requestParams.pageSize,
            "totalElements" to TOTAL_ELEMENTS,
            "list" to list,
        )
    }

}