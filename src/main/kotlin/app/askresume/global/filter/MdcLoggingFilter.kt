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

        log.info("Incoming Request - TraceId: $uuid, IP Address: ${getClientIP(request)}, API Endpoint: [${httpServletRequest.method}]${httpServletRequest.requestURI}")
        chain.doFilter(request, response)
        MDC.clear()
    }

    fun getClientIP(request: HttpServletRequest): String {
        var ip = request.getHeader(X_FORWARDED_FOR)
        if (ip == null) ip = request.getHeader(PROXY_CLIENT_IP)
        if (ip == null) ip = request.getHeader(WL_PROXY_CLIENT_IP)
        if (ip == null) ip = request.getHeader(HTTP_CLIENT_IP)
        if (ip == null) ip = request.getHeader(HTTP_X_FORWARDED_FOR)
        if (ip == null) ip = request.remoteAddr

        return ip
    }

    companion object {
        private const val TRACE_ID = "traceId"
        private const val X_FORWARDED_FOR = "X-Forwarded-For"
        private const val PROXY_CLIENT_IP = "Proxy-Client-IP"
        private const val WL_PROXY_CLIENT_IP = "WL-Proxy-Client-IP"
        private const val HTTP_CLIENT_IP = "HTTP_CLIENT_IP"
        private const val HTTP_X_FORWARDED_FOR = "HTTP_X_FORWARDED_FOR"
    }

}