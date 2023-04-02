package app.askresume.api.job.dto.response;

import com.querydsl.core.annotations.QueryProjection;

public record JobResponse(
        Long id,
        String name
) {

    @QueryProjection
    public JobResponse(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
