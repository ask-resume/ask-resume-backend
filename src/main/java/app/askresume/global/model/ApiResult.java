package app.askresume.global.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ApiResult<T> {

    private final LocalDateTime timestamp = LocalDateTime.now();

    private final int status = HttpStatus.OK.value();
    private T data;

    public ApiResult(T data) {
        this.data = data;
    }
}
