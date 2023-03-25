package app.askresume.global.util;

import lombok.experimental.UtilityClass;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@UtilityClass
public class UriUtil {

    public static URI createUri() {
        return ServletUriComponentsBuilder.fromCurrentRequest()
                .build()
                .toUri();
    }

    public static URI createUri(Long id) {
        return ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id)
                .toUri();
    }
}
