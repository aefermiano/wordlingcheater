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

package com.afermiano.wordlingcheater.guesser;

import com.afermiano.wordlingcheater.Config;
import com.afermiano.wordlingcheater.guesser.scorestrategy.ScoreCalculator;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

public class WordListTest {
    private static final String[] THREE_LETTER = {"ABC", "DEF"};
    private static final String[] TWO_LETTER = {"GH", "IJ"};

    private BufferedReader reader;

    @Before
    public void setup() {
        reader = new BufferedReader(new StringReader(String.format("%s\n%s\n%s\n%s\n%s\n", THREE_LETTER[0], TWO_LETTER[0], "GHIJK", TWO_LETTER[1], THREE_LETTER[1])));
    }

    @After
    public void tearDown() throws IOException {
        reader.close();
    }

    @Test
    public void shouldSelectWordsCorrectly() throws Exception {
        final Config config = Config.builder().scoreCalculator((word) -> 0).build();

        final WordList wordList = new WordList(config, reader, new HashSet<>(Arrays.asList(2, 3)));

        final Set<Word> twoWords = wordList.getWords(2);
        Set<String> expectedWords = new HashSet<>(Arrays.asList(TWO_LETTER));
        for (Word word : twoWords) {
            assertTrue(expectedWords.remove(word.getWord()));
        }
        assertTrue(expectedWords.isEmpty());

        final Set<Word> threeWords = wordList.getWords(3);
        expectedWords = new HashSet<>(Arrays.asList(THREE_LETTER));
        for (Word word : threeWords) {
            assertTrue(expectedWords.remove(word.getWord()));
        }
        assertTrue(expectedWords.isEmpty());
    }

    @Test
    public void shouldNotCrashForInvalidNumberOfWords() throws Exception {
        final Config config = Config.builder().scoreCalculator((word) -> 0).build();

        final WordList wordList = new WordList(config, reader, new HashSet<>(Arrays.asList(2, 3)));
        assertNull(wordList.getWords(4));
    }

    @Test
    public void shouldCalculateScoreCorrectly() throws IOException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        final ScoreCalculator scoreCalculator = (word) -> {
            if (word.equals(TWO_LETTER[0]) || word.equals(THREE_LETTER[0])) {
                return 100;
            }

            return 0;
        };

        final Config config = Config.builder().scoreCalculator(scoreCalculator).build();

        final WordList wordList = new WordList(config, reader, new HashSet<>(Arrays.asList(2, 3)));
        final Set<Word> allWords = new HashSet<>();
        allWords.addAll(wordList.getWords(2));
        allWords.addAll(wordList.getWords(3));

        for (Word word : allWords) {
            final int expectedScore = (word.getWord().equals(TWO_LETTER[0]) || word.getWord().equals(THREE_LETTER[0])) ? 100 : 0;
            assertEquals(expectedScore, word.getScore());
        }
    }

    @Test
    public void shouldNormalizeWords() throws Exception {
        final Config config = Config.builder().scoreCalculator((word) -> 0).build();

        final BufferedReader reader = new BufferedReader(new StringReader("AaáÁâ"));

        final WordList wordList = new WordList(config, reader, new HashSet<>(Arrays.asList(5)));

        final Set<Word> words = wordList.getWords(5);

        assertEquals(1, words.size());
        assertEquals("AAAAA", words.iterator().next().getWord());
    }
}
