package app.askresume.global.model

import org.springframework.data.domain.Page

data class PageResult<T>(
    val page: Int,
    val pageSize: Int,
    val totalElements: Long,
    val list: List<T>
) {

    constructor (page: Page<T>) : this(
        page.number,
        page.size,
        page.totalElements,
        page.content,
    )

}