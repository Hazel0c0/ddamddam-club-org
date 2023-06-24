package kr.co.ddamddam.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;

/**
 * 8~19자의 랜덤 임시비밀번호를 발급하는 기능
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class PasswordGenerator {

    private static final String SPECIAL_CHARACTERS = "!@#$%^&*()";
    private static final String ALPHABET = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String NUMBERS = "0123456789";

    public static String generateRandomPassword() {

        log.info("[PasswordGenerator] generateRandomPassword...");

        SecureRandom random = new SecureRandom();
        StringBuilder password = new StringBuilder();
        int length = random.nextInt(12) + 8;

        // Ensure at least one special character
        password.append(SPECIAL_CHARACTERS.charAt(random.nextInt(SPECIAL_CHARACTERS.length())));

        // Ensure at least one alphabetic character
        password.append(ALPHABET.charAt(random.nextInt(ALPHABET.length())));

        // Ensure at least one numeric character
        password.append(NUMBERS.charAt(random.nextInt(NUMBERS.length())));

        while (password.length() < length) {
            String characterSet = random.nextBoolean() ? ALPHABET : NUMBERS;
            password.append(characterSet.charAt(random.nextInt(characterSet.length())));
        }

        return password.toString();
    }
}
