package app.askresume.domain.submit.constant

enum class SubmitStatus {

    WAITING,    // 대기중
    GENERATING, // 생성중
    FAIL,       // 생성실패
    COMPLETED,  // 생성완료
}