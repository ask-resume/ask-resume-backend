package app.askresume.domain.gpt.constant;

import lombok.Getter;

@Getter
public enum MessageRole {

    SYSTEM("system"), USER("user");


    private String role;

    MessageRole(String role) {
        this.role = role;
    }

    public static MessageRole from(String messageRole) {
        return MessageRole.valueOf(messageRole);
    }

}