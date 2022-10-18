/*
 * Copyright (C) 2022 Antonio Fermiano
 *
 * This file is part of Wordling Cheater.
 *
 * Wordling Cheater is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Wordling Cheater is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Wordling Cheater. If not, see <https://www.gnu.org/licenses/>.
 */

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
