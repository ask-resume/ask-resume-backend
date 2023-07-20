package app.askresume.domain.submit.constant

enum class SubmitDataStatus {

    WAITING, // 대기
    RESEND,  // 재전송 해야함
    SUCCESS, // 성공
    ;

}