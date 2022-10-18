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
import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class WordList {
    private static final Map<Integer, Set<Word>> wordsMap = new HashMap<>();

    public WordList(Config config, BufferedReader reader, Set<Integer> supportedNumberOfLetters) throws IOException {
        final Set<String> allWords = new HashSet<>();
        String line;
        while ((line = reader.readLine()) != null) {
            final String convertedWord = StringUtils.stripAccents(line).toUpperCase();
            allWords.add(convertedWord);
        }

        for (Integer numberOfLetters : supportedNumberOfLetters) {
            final ScoreCalculator scoreCalculator = config.getScoreCalculator();
            scoreCalculator.load(allWords, numberOfLetters);

            final Set<Word> words = new HashSet<>();
            wordsMap.put(numberOfLetters, words);

            for (String word : allWords) {
                if (word.length() != numberOfLetters) {
                    continue;
                }
                final int score = scoreCalculator.calculateScore(word);
                words.add(new Word(word, score));
            }
        }
    }

    public Set<Word> getWords(int numberOfLetters) {
        final Set<Word> words = wordsMap.get(numberOfLetters);
        if (words == null) {
            return null;
        }

        return Collections.unmodifiableSet(words);
    }
}
