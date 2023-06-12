package kr.co.ddamddam.common.common;

/**
 * ResponseDTO 에서 제목 or 본문 미리보기 or 닉네임이
 * 특정 글자수를 넘을 경우 ... 을 붙여 생략시켜주는 공통 클래스
 */
public class TruncateString {

    /**
     * 문자열 생략 메서드
     * @param str - 검사할 문자열
     * @param max - 해당 문자열의 최대 길이
     * @return str 를 최대 길이까지 자른 후, '...' 을 붙인 문자열
     */
    public static String truncate(String str, int max) {
        if (str.length() > max) {
            return str.substring(0, max + 1) + "...";
        }

        return str;
    }

}
