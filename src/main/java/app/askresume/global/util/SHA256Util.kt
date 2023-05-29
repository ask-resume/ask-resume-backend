package app.askresume.global.util

import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

object SHA256Util {

    fun encrypt(str: String): String {
        val sha: String =
            try {
                val sh = MessageDigest.getInstance("SHA-256")
                sh.update(str.toByteArray())
                val byteData = sh.digest()

                val sb = StringBuilder()
                for (i in byteData.indices) {
                    sb.append(
                        ((byteData[i].toInt() and 0xff) + 0x100).toString(16).substring(1)
                    )
                }
                sb.toString()
            } catch (e: NoSuchAlgorithmException) {
                // 암호 알고리즘이 요구되었음에도 불구하고, 현재의 환경에서는 사용 가능하지 않은 경우에 예외를 발생시킵니다.
                throw RuntimeException("암호화 문제발생(SHA256)")
            }

        return sha
    }

}

