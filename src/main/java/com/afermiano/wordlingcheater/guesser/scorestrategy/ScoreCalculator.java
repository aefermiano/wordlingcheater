package com.afermiano.wordlingcheater.guesser.scorestrategy;

import java.util.Set;

public interface ScoreCalculator {
    default void load(Set<String> allWords, int numberOfLetters) {
    }

    int calculateScore(String word);
}
