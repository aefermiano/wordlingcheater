package com.afermiano.wordlingcheater.guesser;

import com.afermiano.wordlingcheater.Config;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class GuesserTest {
    private WordList wordList;
    private Config config;

    @Before
    public void setup() {
        wordList = mock(WordList.class);
        config = Config.builder().scoreCalculator((word) -> 0).build();
    }

    @Test
    public void shouldLoadDataCorrectlyAtCreation() {
        final Guesser guesser = new Guesser(5, wordList);

        assertEquals(5, guesser.getNumberOfLetters());
        verify(wordList, times(1)).getWords(eq(5));
    }

    @Test
    public void shouldReloadDataAtReset() {
        final Guesser guesser = new Guesser(5, wordList);
        guesser.reset(6);

        assertEquals(6, guesser.getNumberOfLetters());
        verify(wordList, times(1)).getWords(eq(6));
    }

    @Test
    public void guessShouldReturnWordsAtScoreOrder() {
        final List<String> words = Arrays.asList("PUDIM", "PEDIR", "POMAR", "TESTE");

        final Set<Word> wordListReturn = new HashSet<>();
        wordListReturn.add(new Word(words.get(1), 50));
        wordListReturn.add(new Word(words.get(0), 100));
        wordListReturn.add(new Word(words.get(3), 0));
        wordListReturn.add(new Word(words.get(2), 23));
        when(wordList.getWords(anyInt())).thenReturn(wordListReturn);

        final Guesser guesser = new Guesser(5, wordList);
        final List<String> guesses = guesser.guess(words.size() + 1);

        for (int i = 0; i < words.size(); i++) {
            assertEquals(words.get(i), guesses.get(i));
        }
    }

    @Test
    public void guessShouldBeConsistentIfCalledMoreThanOnce() {
        final List<String> words = Arrays.asList("PUDIM", "PEDIR", "POMAR", "TESTE");

        final Set<Word> wordListReturn = new HashSet<>();
        wordListReturn.add(new Word(words.get(1), 0));
        wordListReturn.add(new Word(words.get(0), 0));
        wordListReturn.add(new Word(words.get(3), 0));
        wordListReturn.add(new Word(words.get(2), 0));
        when(wordList.getWords(anyInt())).thenReturn(wordListReturn);

        final Guesser guesser = new Guesser(5, wordList);
        final List<String> guesses = guesser.guess(words.size() + 1);
        final List<String> guesses2 = guesser.guess(words.size() + 1);

        assertArrayEquals(guesses.toArray(), guesses2.toArray());
    }

    @Test
    public void guessShouldRespectCountLimit() {
        final List<String> words = Arrays.asList("PUDIM", "PEDIR", "POMAR", "TESTE");

        final Set<Word> wordListReturn = new HashSet<>();
        wordListReturn.add(new Word(words.get(1), 0));
        wordListReturn.add(new Word(words.get(0), 0));
        wordListReturn.add(new Word(words.get(3), 0));
        wordListReturn.add(new Word(words.get(2), 0));
        when(wordList.getWords(anyInt())).thenReturn(wordListReturn);

        final Guesser guesser = new Guesser(5, wordList);
        final List<String> guesses = guesser.guess(2);

        assertEquals(2, guesses.size());
        assertNotEquals(guesses.get(0), guesses.get(1));
        assertTrue(words.contains(guesses.get(0)));
        assertTrue(words.contains(guesses.get(1)));
    }

    @Test
    public void resetShouldClearAvailableOptions() {
        Set<Word> wordListReturn = new HashSet<>();
        wordListReturn.add(new Word("ABCDE", 0));
        wordListReturn.add(new Word("FGHIJ", 0));
        when(wordList.getWords(eq(5))).thenReturn(wordListReturn);

        wordListReturn = new HashSet<>();
        wordListReturn.add(new Word("ABCD", 0));
        wordListReturn.add(new Word("FGHI", 0));
        wordListReturn.add(new Word("JKLM", 0));
        when(wordList.getWords(eq(4))).thenReturn(wordListReturn);

        final Guesser guesser = new Guesser(3, wordList);
        guesser.reset(5);
        guesser.reset(4);

        final List<String> guesses = guesser.guess(10);

        assertEquals(3, guesses.size());
        for (String guess : guesses) {
            assertEquals(4, guess.length());
        }
    }

    @Test
    public void fullScenario() {
        final List<String> words = Arrays.asList("PUDIM", "PEDIR", "POMAR", "TESTE");

        final Set<Word> wordListReturn = new HashSet<>();
        wordListReturn.add(new Word(words.get(1), 0));
        wordListReturn.add(new Word(words.get(0), 0));
        wordListReturn.add(new Word(words.get(3), 0));
        wordListReturn.add(new Word(words.get(2), 0));
        when(wordList.getWords(anyInt())).thenReturn(wordListReturn);

        // ITERATION 1
        final Guesser guesser = new Guesser(5, wordList);
        guesser.applyPattern("MXXXX", "YBBBB");

        List<String> guesses = guesser.guess(words.size() + 1);
        assertEquals(2, guesses.size());
        assertTrue(guesses.contains("PUDIM"));
        assertTrue(guesses.contains("POMAR"));

        // ITERATION 2
        guesser.applyPattern("XXXXX", "BBBBB");

        guesses = guesser.guess(words.size() + 1);
        assertEquals(2, guesses.size());
        assertTrue(guesses.contains("PUDIM"));
        assertTrue(guesses.contains("POMAR"));

        // ITERATION 3
        guesser.applyPattern("PUDIM", "GBBBY");
        guesses = guesser.guess(words.size() + 1);
        assertEquals(1, guesses.size());
        assertEquals("POMAR", guesses.get(0));

    }
}
