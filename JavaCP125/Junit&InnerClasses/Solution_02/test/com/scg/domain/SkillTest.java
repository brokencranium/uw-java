package com.scg.domain;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


/**
 * JUnit test for the Skill class.
 */
public final class SkillTest {
    /** Hourly rate. */
    private static final int HOURLY_RATE =  150;

    /** Skill instance for test. */
    private Skill skill;

    /**
     * Perform test setup.
     */
    @Before
    public void setUp() {
        skill = Skill.valueOf("SOFTWARE_ENGINEER");
    }

    /**
     * Perform test tear down.
     */
    @After
    public void tearDown() {
        skill = null;
    }

    /**
     * Test getName method.
     */
    @Test
    public void testGetName() {
        assertEquals("constructor failed to correctly set name",
                "Software Engineer", skill.getName());
    }

    /**
     * Test getRate method.
     */
    @Test
    public void testGetRate() {
        assertEquals("constructor failed to correctly set rate",
                HOURLY_RATE, skill.getRate());
    }
}
