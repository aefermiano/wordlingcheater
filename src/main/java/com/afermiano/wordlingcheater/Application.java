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
