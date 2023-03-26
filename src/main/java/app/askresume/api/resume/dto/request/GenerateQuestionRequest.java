package app.askresume.api.resume.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GenerateQuestionRequest {

    private String job;

    private String difficulty;

    private String content;

}
