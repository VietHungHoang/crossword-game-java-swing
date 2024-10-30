package utils;

import java.security.SecureRandom;
import java.util.Locale;
import java.util.Objects;
import java.util.Random;

public class RandomString {
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
}