package app.askresume.global.config.jpa

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.AuditorAware
import org.springframework.util.StringUtils
import java.util.*
import javax.servlet.http.HttpServletRequest

class AuditorAwareImpl : AuditorAware<String> {

    @Autowired
    private lateinit var httpServletRequest: HttpServletRequest

    override fun getCurrentAuditor(): Optional<String> {
        var modifiedBy = httpServletRequest.requestURI
        if (!StringUtils.hasText(modifiedBy)) {
            modifiedBy = "unknown"
        }
        return Optional.of(modifiedBy)
    }

}

