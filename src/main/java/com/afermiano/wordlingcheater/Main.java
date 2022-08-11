package com.afermiano.wordlingcheater;

import picocli.CommandLine;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        final int exitCode = new CommandLine(new Application()).execute(args);
        System.exit(exitCode);
    }
}
