package app.askresume;

import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.junit.jupiter.api.Test;

// 해당 부분 내용 추가 후 Commit 금지
public class JasyptEncryptionTest {

    private final String ALGORITHM_NAME = "PBEWithMD5AndTripleDES";

    @Test
    public void jasyptTest() {
        String password = "에배배배 아무것도모름"; // 아무거나 적어놔야 test 됨
        PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
        encryptor.setPoolSize(4);
        encryptor.setPassword(password);
        encryptor.setAlgorithm(ALGORITHM_NAME);

        String content = "에배배배 아무것도모름";    // 암호화 할 내용
        String encryptedContent = encryptor.encrypt(content); // 암호화
        String decryptedContent = encryptor.decrypt(encryptedContent); // 복호화

        System.out.println("Enc : " + encryptedContent);
        System.out.println("Dec: " + decryptedContent);
    }

}
