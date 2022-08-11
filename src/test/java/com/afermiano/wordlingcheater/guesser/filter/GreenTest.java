package com.afermiano.wordlingcheater.guesser.filter;

import org.junit.Before;
import org.junit.Test;

import java.util.Collections;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class GreenTest {
    private Filter filter;

    @Before
    public void setup() {
        filter = new Green();
    }

    @Test
    public void shouldMatchIfConditionIsMet() {
        final boolean matches = filter.matches("PUDIM", "PXXXX", 0, Collections.emptySet());
        assertTrue(matches);
    }

    @Test
    public void shouldNotMatchIfConditionIsNotMet() {
        final boolean matches = filter.matches("PUDIM", "XAXXX", 1, Collections.emptySet());
        assertFalse(matches);
    }
}
