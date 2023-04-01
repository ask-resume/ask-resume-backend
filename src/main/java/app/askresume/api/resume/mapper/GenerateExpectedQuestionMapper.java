package app.askresume.api.resume.mapper;

import app.askresume.api.resume.dto.request.GenerateExpectedQuestionRequest;
import app.askresume.api.resume.dto.request.ResumeDataRequest;
import app.askresume.domain.resume.constant.ResumeType;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class GenerateExpectedQuestionMapper {

    public List<ResumeDataRequest> toResumeData(GenerateExpectedQuestionRequest.MyResume contents) {
        return Stream.of(
                Optional.ofNullable(contents.introduction()).orElse(Collections.emptyList())
                        .stream().map(o -> new ResumeDataRequest(ResumeType.INTRODUCTION.value(), o.content())),
                Optional.ofNullable(contents.career()).orElse(Collections.emptyList())
                        .stream().map(o -> new ResumeDataRequest(ResumeType.CAREER.value(), o.content())),
                Optional.ofNullable(contents.technical()).orElse(Collections.emptyList())
                        .stream().map(o -> new ResumeDataRequest(ResumeType.TECHNICAL.value(), o.content())),
                Optional.ofNullable(contents.project()).orElse(Collections.emptyList())
                        .stream().map(o -> new ResumeDataRequest(ResumeType.PROJECT.value(), o.content())),
                Optional.ofNullable(contents.outsideActivities()).orElse(Collections.emptyList())
                        .stream().map(o -> new ResumeDataRequest(ResumeType.OUTSIDE_ACTIVITIES.value(), o.content())),
                Optional.ofNullable(contents.aac()).orElse(Collections.emptyList())
                        .stream().map(o -> new ResumeDataRequest(ResumeType.AAC.value(), o.content()))
        ).flatMap(Function.identity()).collect(Collectors.toList());
    }
}
