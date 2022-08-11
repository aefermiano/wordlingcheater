package com.afermiano.wordlingcheater.guesser.filter;

import java.util.Set;

public class Yellow implements Filter {

    @Override
    public boolean matches(String wordToEvaluate, String wordEvaluated, int position, Set<Character> charactersNotBlack) {
        final char yellowChar = wordEvaluated.charAt(position);

        if (wordToEvaluate.charAt(position) == yellowChar) {
            return false;
        }

        for (int i = 0; i < wordToEvaluate.length(); i++) {
            if (wordToEvaluate.charAt(i) == yellowChar) {
                return true;
            }
        }

        return false;
    }
}
