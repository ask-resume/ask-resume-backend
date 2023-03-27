package app.askresume.global.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ApiResult<T> {

    private final LocalDateTime timestamp = LocalDateTime.now();

    @Schema(description = "status", example = "200")
    private final int status = HttpStatus.OK.value();
    private T data;

    public ApiResult(T data) {
        this.data = data;
    }
}
