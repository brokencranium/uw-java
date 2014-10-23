package com.scg.domain;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * JUnit test for NonBillableAccount class.
 */
public final class NonBillableAccountTest {
    /** NonBillableAccount instance for test. */
    private NonBillableAccount nonbillableaccount;

    /**
     * Perform test setup.
     */
    @Before
    public void setUp() {
        nonbillableaccount = NonBillableAccount.VACATION;
    }

    /**
     * Perform test tear down.
     */
    @After
    public void tearDown() {
        nonbillableaccount = null;
    }

    /**
     * Test the getName method.
     */
    @Test
    public void testGetName() {
        assertEquals("Vacation", nonbillableaccount.getName());
    }
}
