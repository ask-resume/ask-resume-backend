package app.askresume.domain.resume.constant;

public enum ResumeType {

    INTRODUCTION("introduction"), // 자기소개서
    CAREER("career history"), // 경력
    TECHNICAL("technology stack"), // 기술스택
    PROJECT("project experience"), // 프로젝트 경험
    EDUCATION("education history"), // 교육 이력
    OUTSIDE_ACTIVITIES("outside activities"), // 대외 활동
    AAC("achievements and credentials") // 자격증,어학,수상내역
    ;

    private final String value;

    public String value() {
        return value;
    }

    ResumeType(String value) {
        this.value = value;
    }

}
