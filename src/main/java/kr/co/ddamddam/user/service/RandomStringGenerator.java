package kr.co.ddamddam.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;

/**
 * 랜덤 문자열을 발급하는 기능
 * 임시 비밀번호 발급, 카카오 회원가입시 닉네임 랜덤설정에 사용합니다.
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class RandomStringGenerator {

    private static final String SPECIAL_CHARACTERS = "!@#$%^&*()";
    private static final String ALPHABET = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String NUMBERS = "0123456789";

    public static String generateRandomPassword() {

        log.info("[RandomStringGenerator] generateRandomPassword...");

        SecureRandom random = new SecureRandom();
        StringBuilder password = new StringBuilder();
        int length = 10; // 10자리 고정

        String characters = ALPHABET + NUMBERS + SPECIAL_CHARACTERS;

        for (int i = 0; i < length; i++) {
            password.append(characters.charAt(random.nextInt(characters.length())));
        }

        return password.toString();
    }

    public static String generateRandomNickname() {

        log.info("[RandomStringGenerator] generateRandomNickname...");

        SecureRandom random = new SecureRandom();
        StringBuilder password = new StringBuilder();
        int length = 10; // 10자리 고정

        String characters = ALPHABET + NUMBERS;

        for (int i = 0; i < length; i++) {
            password.append(characters.charAt(random.nextInt(characters.length())));
        }

        return password.toString();
    }
}
