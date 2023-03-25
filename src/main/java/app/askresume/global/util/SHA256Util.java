package app.askresume.global.util;

import lombok.experimental.UtilityClass;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@UtilityClass
public class SHA256Util {

    public static String encrypt(String str) {
        String sha;

        try {
            MessageDigest sh = MessageDigest.getInstance("SHA-256");
            sh.update(str.getBytes());
            byte[] byteData = sh.digest();
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < byteData.length; i++) {
                sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
            }

            sha = sb.toString();

        } catch (NoSuchAlgorithmException e) {
            // 암호 알고리즘이 요구되었음에도 불구하고, 현재의 환경에서는 사용 가능하지 않은 경우에 예외를 발생시킵니다.
            throw new RuntimeException("암호화 문제발생(SHA256)");
        }

        return sha;
    }
}
