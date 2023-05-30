package app.askresume.global.config.querydsl

import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext

@Configuration
class QueryDslConfig {

    @PersistenceContext
    private lateinit var em: EntityManager

    @Bean
    fun init() = JPAQueryFactory(em)

}