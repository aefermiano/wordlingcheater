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

import com.afermiano.wordlingcheater.Constants;
import com.afermiano.wordlingcheater.guesser.filter.Black;
import com.afermiano.wordlingcheater.guesser.filter.Filter;
import com.afermiano.wordlingcheater.guesser.filter.Green;
import com.afermiano.wordlingcheater.guesser.filter.Yellow;
import lombok.Getter;

import java.util.*;

public class Guesser {
    private final WordList wordList;
    private Map<Character, Filter> filters = new HashMap<>();
    @Getter
    private int numberOfLetters;
    private SortedSet<Word> availableWords = buildSortedSet();
    private List<String> guesses = new ArrayList<>();
    private Set<Character> charactersNotBlack = new HashSet<>();

    public Guesser(int numberOfLetters, WordList wordList) {
        this.wordList = wordList;

        reset(numberOfLetters);
        buildFilters();
    }

    private SortedSet<Word> buildSortedSet() {
        final Comparator<Word> comparator = (o1, o2) -> {
            if (o1.getWord().equals(o2.getWord())) {
                return 0;
            }

            if (o1.getScore() == o2.getScore()) {
                return 1;
            }

            return o2.getScore() - o1.getScore();
        };
        return new TreeSet<>(comparator);
    }

    public void reset(int numberOfLetters) {
        this.numberOfLetters = numberOfLetters;

        availableWords.clear();
        availableWords.addAll(wordList.getWords(numberOfLetters));
    }

    private void buildFilters() {
        filters.put(Constants.GREEN_CHARACTER, new Green());
        filters.put(Constants.YELLOW_CHARACTER, new Yellow());
        filters.put(Constants.BLACK_CHARACTER, new Black());
    }

    public void applyPattern(String wordEvaluated, String pattern) {
        // If char has been matched by other color, black information is useless
        charactersNotBlack.clear();
        for (int i = 0; i < pattern.length(); i++) {
            if (pattern.charAt(i) != Constants.BLACK_CHARACTER) {
                charactersNotBlack.add(wordEvaluated.charAt(i));
            }
        }

        final SortedSet<Word> newSet = buildSortedSet();
        for (Word word : availableWords) {
            boolean matched = true;
            for (int i = 0; i < numberOfLetters; i++) {
                final char c = pattern.charAt(i);

                final Filter filter = filters.get(c);
                if (!filter.matches(word.getWord(), wordEvaluated, i, charactersNotBlack)) {
                    matched = false;
                    break;
                }
            }

            if (matched) {
                newSet.add(word);
            }
        }

        availableWords = newSet;
    }

    public List<String> guess(int maxNumberOfGuesses) {
        guesses.clear();

        int i = 0;
        for (Word word : availableWords) {
            if (i >= maxNumberOfGuesses) {
                continue;
            }
            i++;

            guesses.add(word.getWord());
        }

        return guesses;
    }
}
