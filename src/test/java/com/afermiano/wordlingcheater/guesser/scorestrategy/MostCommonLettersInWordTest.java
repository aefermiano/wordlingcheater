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

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class MostCommonLettersInWordTest {

    private ScoreCalculator scoreCalculator;

    @Before
    public void setup() {
        scoreCalculator = new MostCommonLettersInWord();

        // Most common letters = A, then D
        // Word with number of letters different than 3 is discarded
        final Set<String> wordsThreeLetters = new HashSet<>(Arrays.asList("BCE", "AAA", "BBBBBBB", "DDH"));
        scoreCalculator.load(wordsThreeLetters, 3);
    }

    @Test
    public void differentMostFrequentCharsMustHaveHigherScore() {
        final String wordWithMorePoints = "ABD";
        final String wordWithLessPoints = "AZY";

        assertTrue(scoreCalculator.calculateScore(wordWithMorePoints) > scoreCalculator.calculateScore(wordWithLessPoints));
    }

    @Test
    public void shouldNotConsiderRepeatedChars() {
        final String wordWithMorePoints = "ABD";
        final String wordWithLessPoints = "AAA";

        assertTrue(scoreCalculator.calculateScore(wordWithMorePoints) > scoreCalculator.calculateScore(wordWithLessPoints));
    }

    @Test
    public void repeatedCallsMustHaveConsistentResults() {
        final String word1 = "ABD";
        final String word2 = "AZY";

        final int score1 = scoreCalculator.calculateScore(word1);
        final int score2 = scoreCalculator.calculateScore(word2);

        assertEquals(score1, scoreCalculator.calculateScore(word1));
        assertEquals(score2, scoreCalculator.calculateScore(word2));
    }
}
