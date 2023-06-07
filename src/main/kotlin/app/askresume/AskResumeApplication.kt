package app.askresume

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication

@SpringBootApplication
@ConfigurationPropertiesScan
class AskResumeApplication

fun main(args: Array<String>) {
    runApplication<AskResumeApplication>(*args)
}

