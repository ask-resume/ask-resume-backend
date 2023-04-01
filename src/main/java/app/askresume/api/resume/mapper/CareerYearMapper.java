package app.askresume.api.resume.mapper;

import org.springframework.stereotype.Component;

@Component
public class CareerYearMapper {

    public String toCareer(int careerYear) {
        if (careerYear == 0) return "newcomer";
        else if (careerYear == 10) return "more than 10 years";
        else return careerYear + "year";
    }
}
