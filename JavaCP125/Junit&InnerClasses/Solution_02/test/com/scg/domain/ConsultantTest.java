/*
 * Created on Dec 19, 2007
 */
package com.scg.domain;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.scg.util.Name;

/**
 * JUnit test for the Consultant class.
 */
public final class ConsultantTest {
    /** String constant "Random". */
    private static final String RANDOM = "Random";

    /** String constant "J.". */
    private static final String J_DOT = "J.";

    /** String constant "Programmer". */
    private static final String PROGRAMMER = "Programmer";

    /** Name instance for test. */
    private Name programmerName;
    /** Consultant instance for test. */
    private Consultant programmer;

    /**
     * Perform test setup.
     */
    @Before
    public void setUp() {
        programmerName = new Name(PROGRAMMER, J_DOT, RANDOM);
        programmer = new Consultant(new Name(PROGRAMMER, J_DOT, RANDOM));
    }

    /**
     * Tests getName method.
     */
    @Test
    public void testGetName() {
        assertEquals(programmerName, programmer.getName());
    }

    /**
     * Tests toString method.
     */
    @Test
    public void testToString() {
        System.out.println(programmer);
    }
}
