package app.askresume.api.submit.dto

import io.swagger.v3.oas.annotations.Parameter
import org.springframework.web.bind.annotation.RequestParam

class SubmitParam {

    data class MySubmits(
        @field:Parameter(name = "page", description = "현재 페이지", required = true, example = "1")
        @RequestParam
        val page: Int,

        @field:Parameter(name = "pageSize", description = "페이지 데이터 수", required = true, example = "10")
        @RequestParam
        val pageSize: Int,
    )

}