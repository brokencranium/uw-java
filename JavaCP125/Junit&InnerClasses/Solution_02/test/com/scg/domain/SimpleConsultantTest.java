package com.scg.domain;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.scg.util.Name;

/**
 * JUnit Test for Simple Consultant class.
 */
public final class SimpleConsultantTest {
    /** String constant "Susan". */
    private static final String SUSAN = "Susan";

    /** String constant "J.". */
    private static final String J_DOT = "J.";

    /** String constant "Consultant". */
    private static final String CONSULTANT = "Consultant";

    /** Consultant for tested. */
    private Consultant simpleconsultant;

    /**
     * Perform test setup.
     */
    @Before
    public void setUp() {
        simpleconsultant = new com.scg.domain.Consultant(new Name(CONSULTANT, SUSAN, J_DOT));
    }

    /**
     * Perform test tear down.
     */
    @After
    public void tearDown() {
        simpleconsultant = null;
    }

    /**
     * Test getName method.
     * @throws Exception if the test fails
     */
    @Test
    public void testGetName() throws Exception {
        assertEquals(new Name(CONSULTANT, SUSAN, J_DOT), simpleconsultant.getName());
    }
}
