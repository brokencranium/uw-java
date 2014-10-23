package com.scg.domain;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.scg.util.Name;

/**
 * JUnit test for ClientAccount class.
 */
public final class ClientAccountTest {
    /** String constant for "FooBar.com". */
    private static final String FOOBAR_DOT_COM = "FooBar.com";
    /** String constant for "Client". */
    private static final String CLIENT = "Client";
    /** String constant for "J.". */
    private static final String J_DOT = "J.";
    /** String constant for "Random". */
    private static final String RANDOM = "Random";
    /** ClientAccount for test. */
    private ClientAccount client;

    /**
     * Perform test setup.
     */
    @Before
    public void setUp() {
        client = new ClientAccount(FOOBAR_DOT_COM,
                                   new Name(CLIENT, J_DOT, RANDOM));
    }

    /**
     * Perform test tear down.
     */
    @After
    public void tearDown() {
        client = null;
    }

    /**
     * Test getName and setName methods.
     */
    @Test
    public void testSetGetName() {
        assertEquals(FOOBAR_DOT_COM, client.getName());
    }

    /**
     * Test getContact and setContact methods.
     */
    @Test
    public void testSetGetContact() {
        final Name[] tests = {new com.scg.util.Name(), null};

        for (int i = 0; i < tests.length; i++) {
            client.setContact(tests[i]);
            assertEquals(tests[i], client.getContact());
        }
    }
}
