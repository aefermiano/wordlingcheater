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
