package app.askresume.api.resume.mapper;

import app.askresume.api.resume.dto.request.GenerateExpectedQuestionRequest;
import app.askresume.api.resume.dto.request.ResumeDataRequest;
import app.askresume.domain.resume.constant.ResumeType;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class GenerateExpectedQuestionMapper {

    public List<ResumeDataRequest> toResumeData(GenerateExpectedQuestionRequest.MyResume contents) {
        List<ResumeDataRequest> returnData = new ArrayList<>();

        contents.introduction().forEach(o -> {
            returnData.add(new ResumeDataRequest(ResumeType.INTRODUCTION.full(), o.content()));
        });

        contents.career().forEach(o -> {
            returnData.add(new ResumeDataRequest(ResumeType.CAREER.full(), o.content()));
        });

        contents.technical().forEach(o -> {
            returnData.add(new ResumeDataRequest(ResumeType.TECHNICAL.full(), o.content()));
        });

        contents.project().forEach(o -> {
            returnData.add(new ResumeDataRequest(ResumeType.PROJECT.full(), o.content()));
        });

        contents.outsideActivities().forEach(o -> {
            returnData.add(new ResumeDataRequest(ResumeType.OUTSIDE_ACTIVITIES.full(), o.content()));
        });

        contents.aac().forEach(o -> {
            returnData.add(new ResumeDataRequest(ResumeType.AAC.full(), o.content()));
        });


        return returnData;
    }

}
