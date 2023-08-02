package app.askresume.global.filter

import app.askresume.global.util.LoggerUtil.logger
import org.slf4j.MDC
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component
import java.util.*
import javax.servlet.Filter
import javax.servlet.FilterChain
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.http.HttpServletRequest

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
class MdcLoggingFilter : Filter {

    private val log = logger()

    override fun doFilter(request: ServletRequest, response: ServletResponse, chain: FilterChain) {
        val uuid = UUID.randomUUID().toString()
        MDC.put(TRACE_ID, uuid)

        val httpServletRequest = request as HttpServletRequest

        log.info("Incoming Request - TraceId: $uuid, IP Address: ${request.remoteAddr}, API Endpoint: ${httpServletRequest.requestURI}, HTTP Method: ${httpServletRequest.method}")
        log.info("parameter : ${test(request)}")
        chain.doFilter(request, response)
        MDC.clear()
    }

    companion object {
        const val TRACE_ID = "traceId"
    }


    fun test(request: HttpServletRequest): String {
        // 모든 파라미터 이름과 값을 가져와서 List에 추가

        val parameterList = mutableListOf<FilterParameter>()
        val parameterMap = request.parameterMap
        for ((paramName, paramValues) in parameterMap) {
            for (paramValue in paramValues) {
                parameterList.add(FilterParameter(paramName, paramValue))
            }
        }

        // List를 JSON 형태의 문자열로 직렬화하여 출력
        return parameterList.toString()
    }
}

data class FilterParameter(val name: String, val value: String)