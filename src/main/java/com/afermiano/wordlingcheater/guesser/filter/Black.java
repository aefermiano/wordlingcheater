package com.afermiano.wordlingcheater.guesser.filter;

import java.util.Set;

public class Black implements Filter {

    @Override
    public boolean matches(String wordToEvaluate, String wordEvaluated, int position, Set<Character> charactersNotBlack) {

        final char blackChar = wordEvaluated.charAt(position);

        if (charactersNotBlack.contains(blackChar)) {
            return true;
        }

        for (int i = 0; i < wordToEvaluate.length(); i++) {
            if (wordToEvaluate.charAt(i) == blackChar) {
                return false;
            }
        }

        return true;
    }
}
