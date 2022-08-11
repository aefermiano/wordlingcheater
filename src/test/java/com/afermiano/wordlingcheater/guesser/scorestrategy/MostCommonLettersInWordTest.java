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
