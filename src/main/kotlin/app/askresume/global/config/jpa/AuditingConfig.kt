package app.askresume.global.config.jpa

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaAuditing


@Configuration
@EnableJpaAuditing
class AuditingConfig {

    @Bean
    fun auditorAware() = AuditorAwareImpl()

}