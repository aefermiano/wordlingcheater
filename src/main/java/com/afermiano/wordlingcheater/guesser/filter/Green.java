package com.afermiano.wordlingcheater.guesser.filter;

import java.util.Set;

public class Green implements Filter {

    @Override
    public boolean matches(String wordToEvaluate, String wordEvaluated, int position, Set<Character> charactersNotBlack) {
        return (wordToEvaluate.charAt(position) == wordEvaluated.charAt(position));
    }
}
