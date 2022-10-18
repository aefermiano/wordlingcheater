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
