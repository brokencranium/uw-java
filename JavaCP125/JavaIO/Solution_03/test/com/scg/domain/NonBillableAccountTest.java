package com.scg.domain;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * JUnit test for NonBillableAccount class.
 */

public final class NonBillableAccountTest {
    /**
     * Tests the getName method.
     */
    @Test
    public void testGetName() {
        assertEquals("Vacation", NonBillableAccount.VACATION.getName());
    }
}
