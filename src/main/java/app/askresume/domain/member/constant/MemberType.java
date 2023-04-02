package app.askresume.domain.member.constant;

import java.util.Arrays;
import java.util.List;

public enum MemberType {

    LOCAL, GOOGLE;

    public static MemberType from(String type) {
        return MemberType.valueOf(type.toUpperCase());
    }

    public static boolean isMemberType(String type) {
        List<MemberType> memberTypes = Arrays.stream(MemberType.values())
                .filter(memberType -> memberType.name().equals(type)).toList();
        return memberTypes.size() != 0;
    }

}
