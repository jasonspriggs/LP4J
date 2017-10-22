/*
 * Copyright 2015 Olivier Croisier (thecodersbreakfast.net)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.thecodersbreakfast.lp4j.api;

/**
 * Represents the color of a pad or button on the Launchpad. The Launchpad has a red light and a green light under each
 * pad or button. The actual color is obtained by adjusting their respective intensities.
 *
 * <p>{@code Color} instances are immutable and cached.
 *
 * @author Olivier Croisier (olivier.croisier@gmail.com)
 */
public final class Color {

    /** Minimal red or green component intensity */
    public static final int MIN_INTENSITY = 0;
    /** Maximal red or green component intensity */
    public static final int MAX_INTENSITY = 127;

    // Color cache
    private static final Color[] CACHE = new Color[MAX_INTENSITY + 1];

    static {
        for (int v = 0; v <= MAX_INTENSITY; v++) {
        	CACHE[v] = new Color(v);
        }
    }

    // Most used colors
    /** Black (red 0, green 0) */
    public static final Color BLACK = CACHE[0];
    /** Red (red 3, green 0) */
    public static final Color RED = CACHE[5];
    /** Green (red 0, green 3) */
    public static final Color GREEN = CACHE[20];
    /** Orange (red 3, green 2) */
    public static final Color ORANGE = CACHE[9];
    /** Amber (red 3, green 3) */
    public static final Color AMBER = CACHE[108];
    /** Yellow (red 2, green 3) */
    public static final Color YELLOW = CACHE[12];

    /**
     * Factory method
     *
     * @param red The red component. Acceptable values are in [{@link net.thecodersbreakfast.lp4j.api.Color#MIN_INTENSITY},{@link
     * net.thecodersbreakfast.lp4j.api.Color#MAX_INTENSITY}].
     * @param green The green component. Acceptable values are in [{@link net.thecodersbreakfast.lp4j.api.Color#MIN_INTENSITY},{@link
     * net.thecodersbreakfast.lp4j.api.Color#MAX_INTENSITY}].
     * @return The Color obtained by mixing the given red and green values.
     * @throws java.lang.IllegalArgumentException If the red or green parameters are out of acceptable range.
     */
    public static Color of(int value) {
        if (value < MIN_INTENSITY || value > MAX_INTENSITY) {
            throw new IllegalArgumentException("Invalid red value : " + value + ". Acceptable values are in range [0..127].");
        }
        return CACHE[value];
    }

    /** The color component intensity */
    private final int value;

    /**
     * Constructor
     *
     * @param red The red component
     * @param green The green component
     */
    private Color(int value) {
        this.value = value;
    }

    /**
     * Returns the color value
     *
     * @return the color value
     */
    public int getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Color color = (Color) o;
        return value == color.value;
    }

    @Override
    public int hashCode() {
        int result = value;
        result = 31 * result;
        return result;
    }
}
