package com.game;

/**
 * Pair class
 * inspired by tuples and pair in C++.
 *
 * @param <U> The type of the first element
 * @param <V> The type of the second element
 */
public class Pair<U, V> {
    /**
     * The first element.
     */
    private U first;
    /**
     * The second element.
     */
    private V second;

    /**
     * Pair.
     *
     * @param first  The first element
     * @param second The second element
     */
    public Pair(U first, V second) {
        this.first = first;
        this.second = second;
    }

    /**
     * Get the first element.
     *
     * @return The first element
     */
    public U getFirst() {
        return first;
    }

    /**
     * Get the second element.
     *
     * @return The second element
     */
    public V getSecond() {
        return second;
    }
}
