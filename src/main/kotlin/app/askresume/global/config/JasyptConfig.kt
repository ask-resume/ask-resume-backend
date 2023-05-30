package app.askresume.global.config

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
@EnableEncryptableProperties
class JasyptConfig {

    @Value("\${jasypt.encryptor.password}")
    private lateinit var password: String

    @Bean
    fun jasyptStringEncryptor(): PooledPBEStringEncryptor {
        val encryptor = PooledPBEStringEncryptor()
        encryptor.setPoolSize(4)
        encryptor.setPassword(password)
        encryptor.setAlgorithm("PBEWithMD5AndTripleDES")
        return encryptor
    }

}