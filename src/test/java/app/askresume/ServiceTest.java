package app.askresume;

import org.junit.jupiter.api.AfterEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public abstract class ServiceTest {

    @Autowired
    private DatabaseCleanup databaseCleanup;

    @AfterEach
    void setUp() {
        databaseCleanup.execute();
    }
}

