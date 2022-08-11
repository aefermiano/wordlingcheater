package com.afermiano.wordlingcheater.guesser.filter;

import org.junit.Before;
import org.junit.Test;

import java.util.Collections;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class YellowTest {
    private Filter filter;

    @Before
    public void setup() {
        filter = new Yellow();
    }

    @Test
    public void shouldMatchIfConditionIsMet() {
        final boolean matches = filter.matches("PUDIM", "XIXXX", 1, Collections.emptySet());
        assertTrue(matches);
    }

    @Test
    public void shouldNotMatchIfMainConditionIsNotMet() {
        final boolean matches = filter.matches("PUDIM", "AXXXX", 0, Collections.emptySet());
        assertFalse(matches);
    }

    @Test
    public void shouldNotMatchIfYellowCharMatchesExactly() {
        final boolean matches = filter.matches("PUDIM", "PXXXX", 0, Collections.emptySet());
        assertFalse(matches);
    }
}
