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
