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

        log.info("Incoming Request - TraceId: $uuid, IP Address: ${request.remoteAddr}, API Endpoint: [${httpServletRequest.method}]${httpServletRequest.requestURI}")
        chain.doFilter(request, response)
        MDC.clear()
    }

    companion object {
        const val TRACE_ID = "traceId"
    }

}