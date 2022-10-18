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

import asg.cliche.ShellFactory;
import com.afermiano.wordlingcheater.guesser.Guesser;
import com.afermiano.wordlingcheater.guesser.WordList;
import lombok.Cleanup;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Collections;
import java.util.Set;
import java.util.concurrent.Callable;

@Command(name = "wordling_cheater", description = "Helps you cheat on Wordling! mobile game")
public class Application implements Callable<Integer> {
    @Option(names = {"-d", "--dictionaryFile"}, description = "List of words in the desired language")
    private String dictionaryFile;
    @Parameters(arity = "1", description = "Supported word length")
    private Set<Integer> supportedSizes;

    @Override
    public Integer call() throws Exception {
        final Config config = Config.builder().validNumberOfLetters(supportedSizes).build();

        @Cleanup final BufferedReader reader = new BufferedReader(new FileReader(dictionaryFile));
        final WordList wordList = new WordList(config, reader, supportedSizes);
        final Guesser guesser = new Guesser(Collections.min(supportedSizes), wordList);

        ShellFactory.createConsoleShell("hello", "", new Cli(config, guesser)).commandLoop();

        return 0;
    }
}
