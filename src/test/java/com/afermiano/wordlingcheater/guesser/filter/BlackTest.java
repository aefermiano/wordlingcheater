package com.afermiano.wordlingcheater.guesser.filter;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class BlackTest {
    private Filter filter;

    @Before
    public void setup() {
        filter = new Black();
    }

    @Test
    public void shouldMatchIfConditionIsMet() {
        final boolean matches = filter.matches("PUDIM", "AXXXX", 0, Collections.emptySet());
        assertTrue(matches);
    }

    @Test
    public void shouldNotMatchIfMainConditionIsNotMet() {
        final boolean matches = filter.matches("PUDIM", "UXXXX", 0, Collections.emptySet());
        assertFalse(matches);
    }

    @Test
    public void shouldMatchIfCharIsAlreadyCoveredByAnotherColor() {
        final boolean matches = filter.matches("PUDIM", "UXXXX", 0, new HashSet<>(Arrays.asList('U')));
        assertTrue(matches);
    }
}
