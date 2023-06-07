package app.askresume

import org.jasypt.encryption.pbe.PooledPBEStringEncryptor
import org.junit.jupiter.api.Test

// 해당 부분 내용 추가 후 Commit 금지
class JasyptEncryptionTest {

    val ALGORITHM_NAME: String = "PBEWithMD5AndTripleDES"

    @Test
    fun jasyptTest() {
        val password = "1111" // 아무거나 적어놔야 test 됨
        val encryptor = PooledPBEStringEncryptor()
        encryptor.setPoolSize(4)
        encryptor.setPassword(password)
        encryptor.setAlgorithm(ALGORITHM_NAME)

        val content = "ask_resume_prod"    // 암호화 할 내용
        val encryptedContent = encryptor.encrypt(content) // 암호화
        val decryptedContent = encryptor.decrypt(encryptedContent) // 복호화

        println("Enc : $encryptedContent")
        println("Dec: $decryptedContent")
    }
}
