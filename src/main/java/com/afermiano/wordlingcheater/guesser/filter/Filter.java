package com.afermiano.wordlingcheater.guesser.filter;

import java.util.Set;

public interface Filter {
    boolean matches(String wordToEvaluate, String wordEvaluated, int position, Set<Character> charactersNotBlack);
}
