package com.scg.util;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


/**
 * JUnit test for the Address class.
 */
public final class AddressTest {
    /** Test street address. */
    private static final String STREET_NUMBER = "1024 Kilobyte Dr.";

    /** Test city. */
    private static final String CITY = "Silicone Gulch";

    /** Test zip code. */
    private static final String ZIP_CODE = "94105";

    /** The object under test. */
    private Address address;

    /**
     * Set up the test fixture.
     */
    @Before
    public void setUp() {
        address = new Address(STREET_NUMBER,
                  CITY, StateCode.CA, ZIP_CODE);
    }

    /**
     * Tear down the test fixture.
     */
    @After
    public void tearDown() {
        address = null;
    }

    /**
     * Test the getters.
     */
    @Test
    public void testAccessors() {
        assertEquals(STREET_NUMBER, address.getStreetNumber());
        assertEquals(CITY, address.getCity());
        assertEquals(StateCode.CA, address.getState());
        assertEquals(ZIP_CODE, address.getPostalCode());
    }

    /**
     * Test the toString method.
     */
    @Test
    public void testToString() {
        final String testString = "1024 Kilobyte Dr.\nSilicone Gulch, CA 94105";
        assertEquals("print() failed", testString, address.toString());
    }
}
