package app.askresume.global.util

import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import java.net.URI

object UriUtil {

    fun createUri(): URI {
        return ServletUriComponentsBuilder.fromCurrentRequest()
            .build()
            .toUri()
    }

    fun createUri(id: Long?): URI {
        return ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(id)
            .toUri()
    }
}