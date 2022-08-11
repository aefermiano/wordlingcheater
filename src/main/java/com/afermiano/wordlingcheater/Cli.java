package com.afermiano.wordlingcheater;

import asg.cliche.Command;
import com.afermiano.wordlingcheater.guesser.Guesser;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Cli {
    private final Config config;
    private final Guesser guesser;

    private Set<Character> validPatternChars = new HashSet<>(Arrays.asList(Constants.BLACK_CHARACTER, Constants.GREEN_CHARACTER, Constants.YELLOW_CHARACTER));

    public Cli(Config config, Guesser guesser) {
        this.config = config;
        this.guesser = guesser;
    }

    @Command
    public boolean reset(int numberOfLetters) {
        if (!config.getValidNumberOfLetters().contains(numberOfLetters)) {
            return false;
        }

        guesser.reset(numberOfLetters);

        return true;
    }

    @Command
    public boolean reset() {
        guesser.reset(guesser.getNumberOfLetters());

        return true;
    }

    @Command
    public boolean applyPattern(String wordEvaluated, String pattern) {
        wordEvaluated = wordEvaluated.toUpperCase();
        pattern = pattern.toUpperCase();

        if (wordEvaluated.length() != guesser.getNumberOfLetters() ||
                pattern.length() != guesser.getNumberOfLetters()) {
            return false;
        }

        for (int i = 0; i < pattern.length(); i++) {
            if (!validPatternChars.contains(pattern.charAt(i))) {
                return false;
            }
        }

        guesser.applyPattern(wordEvaluated, pattern);

        return true;
    }

    @Command
    public List<String> guess() {
        return guesser.guess(1);
    }

    @Command
    public List<String> guess(int maxNumberOfGuesses) {
        if (maxNumberOfGuesses <= 0) {
            return null;
        }

        return guesser.guess(maxNumberOfGuesses);
    }

    @Command
    public int getNumberOfLetters() {
        return guesser.getNumberOfLetters();
    }
}
