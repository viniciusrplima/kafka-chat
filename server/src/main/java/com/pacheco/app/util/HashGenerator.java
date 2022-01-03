package com.pacheco.app.util;

public class HashGenerator {

    public static String generateHashedName(String name, int hashSize) {
        return name + generateHash(hashSize);
    }

    public static String generateHash(int size) {
        String hash = "";

        for (int i = 0; i < size; i++) {
            hash += Integer.toHexString(randInt(0, 15));
        }

        return hash;
    }

    private static int randInt(int min, int max) {
        return (int) (Math.random() * (max - min)) + min;
    }
}
