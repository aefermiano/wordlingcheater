package com.afermiano.wordlingcheater;

import com.afermiano.wordlingcheater.guesser.Guesser;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class CliTest {
    private Config config;
    private Guesser guesser;
    private Cli cli;

    @Before
    public void setup() {
        config = Config.builder().validNumberOfLetters(new HashSet<>(Arrays.asList(2, 3))).build();
        guesser = Mockito.mock(Guesser.class);
        cli = new Cli(config, guesser);
    }

    @Test
    public void resetWithParametersShouldCallTheCorrectApi() {
        final boolean ret = cli.reset(3);

        assertTrue(ret);

        verify(guesser, times(1)).reset(eq(3));
    }

    @Test
    public void resetWithParametersShouldFailIfProvidedNumberOfLettersIsWrong() {
        final boolean ret = cli.reset(100);

        assertFalse(ret);

        verify(guesser, never()).reset(anyInt());
    }

    @Test
    public void emptyResetShouldUseTheCorrectParameters() {
        when(guesser.getNumberOfLetters()).thenReturn(100);

        final boolean ret = cli.reset();

        assertTrue(ret);

        verify(guesser, times(1)).reset(100);
    }

    @Test
    public void applyPatternShouldCallTheCorrectApi() {
        final String word = "PUDIM";
        final String pattern = "BGYGB";

        when(guesser.getNumberOfLetters()).thenReturn(word.length());
        final boolean ret = cli.applyPattern(word, pattern);

        assertTrue(ret);

        verify(guesser, times(1)).applyPattern(eq(word), eq(pattern));
    }

    @Test
    public void applyPatternShouldTransformCharsToUpperCase() {
        final String word = "pudim";
        final String pattern = "bgygb";

        when(guesser.getNumberOfLetters()).thenReturn(word.length());
        final boolean ret = cli.applyPattern(word, pattern);

        assertTrue(ret);

        verify(guesser, times(1)).applyPattern(eq(word.toUpperCase()), eq(pattern.toUpperCase()));
    }

    @Test
    public void applyPatternShouldFailForWrongSizes() {
        final String word = "PUDIM";
        final String pattern = "BGYGB";

        when(guesser.getNumberOfLetters()).thenReturn(word.length());

        boolean ret = cli.applyPattern(word + 'X', pattern);
        assertFalse(ret);

        ret = cli.applyPattern(word, pattern + 'G');
        assertFalse(ret);

        verify(guesser, never()).applyPattern(eq(word), eq(pattern));
    }

    @Test
    public void applyPatternShouldFailForInvalidChars() {
        final String word = "PUDIM";
        final String pattern = "XGYGB";

        when(guesser.getNumberOfLetters()).thenReturn(word.length());
        final boolean ret = cli.applyPattern(word, pattern);

        assertFalse(ret);

        verify(guesser, never()).applyPattern(eq(word), eq(pattern));
    }

    @Test
    public void emptyGuessShouldCallTheCorrectApi() {
        final List<String> expectedGuesses = Arrays.asList("PUDIM");

        when(guesser.guess(anyInt())).thenReturn(expectedGuesses);
        final List<String> guesses = cli.guess();

        assertEquals(1, guesses.size());
        assertEquals(expectedGuesses.get(0), guesses.get(0));

        verify(guesser, times(1)).guess(1);
    }

    @Test
    public void guessWithParametersShouldCallTheCorrectApi() {
        final List<String> expectedGuesses = Arrays.asList("PUDIM", "PEDIR");

        when(guesser.guess(anyInt())).thenReturn(expectedGuesses);
        final List<String> guesses = cli.guess(2);

        assertEquals(2, guesses.size());
        assertEquals(expectedGuesses, guesses);

        verify(guesser, times(1)).guess(2);
    }

    @Test
    public void guessWithParametersShouldFailForInvalidMaxNumberOfGuesses() {
        final List<String> guesses = cli.guess(0);

        assertNull(guesses);

        verify(guesser, never()).guess(anyInt());
    }

    @Test
    public void getNumberOfLettersShouldReturnTheCorrectValue() {
        when(guesser.getNumberOfLetters()).thenReturn(99);

        final int ret = cli.getNumberOfLetters();
        assertEquals(99, ret);
    }

}
