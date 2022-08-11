package com.afermiano.wordlingcheater.guesser.scorestrategy;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class MostCommonLettersInWord implements ScoreCalculator {
    private final int[] scoreMap = new int[((int) 'Z' - (int) 'A') + 1];
    private Set<Character> usedChars = new HashSet<>();

    @Override
    public void load(Set<String> allWords, int numberOfLetters) {
        Arrays.fill(scoreMap, 0);

        for (String word : allWords) {
            if (word.length() != numberOfLetters) {
                continue;
            }

            for (int i = 0; i < word.length(); i++) {
                final char c = word.charAt(i);
                scoreMap[getPositionInMap(c)]++;
            }
        }
    }

    private int getPositionInMap(char c) {
        return (int) c - (int) 'A';
    }

    @Override
    public int calculateScore(String word) {
        int score = 0;
        usedChars.clear();
        for (int i = 0; i < word.length(); i++) {
            final char c = word.charAt(i);

            // Tries to prioritize words with different letters in order to maximize chances of "yellow"
            if (!usedChars.add(c)) {
                continue;
            }

            score += scoreMap[getPositionInMap(c)];
        }

        return score;
    }
}
