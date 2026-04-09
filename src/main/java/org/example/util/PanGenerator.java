package org.example.util;

import org.springframework.stereotype.Component;

import java.security.SecureRandom;

@Component
public class PanGenerator {
    private final SecureRandom random = new SecureRandom();

    public String randomCardNumber() {
        var str = new StringBuilder();
        for (int i = 0; i < 15; i++) {
            str.append(random.nextInt(10));
        }
        int sum = 0;
        boolean shouldDouble = false;
        for (int i = 14; i >= 0; i--) {
            int x = str.charAt(i) - '0';
            if (shouldDouble) {
                x = x * 2;
                if (x > 9) {
                    x -= 9;
                }
            }
            sum += x;
            shouldDouble = !shouldDouble;
        }
        int controlDigit = (10 - (sum % 10)) % 10;
        return str.append(controlDigit).toString();
    }
}
