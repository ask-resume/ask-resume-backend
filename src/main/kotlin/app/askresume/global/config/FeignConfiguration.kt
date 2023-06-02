package app.askresume.global.config

import app.askresume.global.error.FeignClientExceptionErrorDecoder
import feign.Logger
import feign.Retryer
import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.cloud.openfeign.FeignClientsConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import

@Configuration
@EnableFeignClients(basePackages = ["app.askresume"]) // todo 패키지명 수정
@Import(FeignClientsConfiguration::class)
class FeignConfiguration {

    @Bean
    fun feignLoggerLevel() = Logger.Level.FULL

    @Bean
    fun errorDecoder() = FeignClientExceptionErrorDecoder()

    @Bean
    fun retryer() = Retryer.Default(1000, 2000, 3)

}

