package com.afermiano.wordlingcheater;

import com.afermiano.wordlingcheater.guesser.scorestrategy.MostCommonLettersInWord;
import com.afermiano.wordlingcheater.guesser.scorestrategy.ScoreCalculator;
import lombok.Builder;
import lombok.Getter;

import java.util.Set;

@Builder
@Getter
public class Config {
    @Builder.Default
    private ScoreCalculator scoreCalculator = new MostCommonLettersInWord();
    private Set<Integer> validNumberOfLetters;
}
