package app.askresume.api.submit.facade

import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class SubmitFacade {

    companion object {
        const val TOTAL_ELEMENTS = 230613
    }

    fun findMySubmits(pageable: Pageable): Map<String, Any> {
        val page = pageable.pageNumber
        val pageSize = pageable.pageSize
        val list = mutableListOf<Map<String, Any>>()

        val firstElement = TOTAL_ELEMENTS - page.coerceAtLeast(0) * pageSize
        val lastElement = (TOTAL_ELEMENTS - ((page + 1) * pageSize) + 1).coerceAtLeast(1)

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
            "page" to page,
            "pageSize" to pageSize,
            "totalElements" to TOTAL_ELEMENTS,
            "list" to list,
        )
    }

}