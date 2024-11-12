package utils;

import java.io.Serializable;
import java.security.SecureRandom;
import java.util.Locale;
import java.util.Objects;
import java.util.Random;

public class RandomString implements Serializable {
    private static final long serialVersionUID = 1L;
    public static final String UPPER = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    public static final String LOWER = UPPER.toLowerCase(Locale.ROOT);
    public static final String DIGITS = "0123456789";
    public static final String SPECIAL = "!@#$%^&*()_+";
    public static final String ALPHANUM_SPECIAL = UPPER + LOWER + DIGITS + SPECIAL;
    private final Random random;
    private final char[] symbols;
    private final char[] buf; 
    public RandomString(int length, Random random, String symbols) {
        if (length < 1)
            throw new IllegalArgumentException();
        if (symbols.length() < 2)
            throw new IllegalArgumentException();
        this.random = Objects.requireNonNull(random);
        this.symbols = symbols.toCharArray();
        this.buf = new char[length];
    }

    /**
     * Create an alphanumeric string generator with special characters.
     */
    public RandomString(int length, Random random) {
        this(length, random, ALPHANUM_SPECIAL);
    }

    /**
     * Create an alphanumeric string with special characters from a secure generator.
     */
    public RandomString(int length) {
        this(length, new SecureRandom());
    }

    /**
     * Create session identifiers with special characters.
     */
    public RandomString() {
        this(21);
    }

    public String nextString() {
        for (int idx = 0; idx < buf.length; ++idx)
            buf[idx] = symbols[random.nextInt(symbols.length)];
        return new String(buf);
    }

    // Random keyword
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final Random RANDOM = new Random();

    public static String generateRandomString() {
        int length = RANDOM.nextInt(3) + 3; // Độ dài từ 3 đến 5 ký tự
        StringBuilder sb = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            int index = RANDOM.nextInt(CHARACTERS.length());
            sb.append(CHARACTERS.charAt(index));
        }
        System.out.println("RANDOM STRING: " +sb.toString());

        return sb.toString();
    }
}