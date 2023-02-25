package com.callum.squareboard;

public class Hash {
    private static final long constant = 1000000009L;

    /**
     * Static method to take a string and generate hashcodes.
     * @param key the key
     * @return the hashcode.
     */

    public static long hashCode(String key){
        long hash = key.charAt(0) + constant;

        for (int i = 1; i < key.length(); i++) {
            char c = key.charAt(i);
            int unicodeValue = c;

            hash += unicodeValue + ((constant + i)) * hash;
        }

        return hash;
    }
}