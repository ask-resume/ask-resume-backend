package app.askresume.api.testing.dto.request;

import javax.validation.constraints.NotNull;

public record TestingRequest(
        @NotNull
        String value1,

        @NotNull
        String value2
) {
}
