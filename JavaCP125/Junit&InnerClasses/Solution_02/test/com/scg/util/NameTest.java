package com.scg.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * JUnit test for Name class.
 */
public final class NameTest {
    /** String constant "Coder". */
    private static final String CODER = "Coder";
    /** String constant "John". */
    private static final String JOHN = "John";
    /** String constant "Quincy". */
    private static final String QUINCY = "Quincy";
    /** String constant "NFN". */
    private static final String NFN = "NFN";
    /** String constant "NLN". */
    private static final String NLN = "NLN";
    /** String constant "NMN". */
    private static final String NMN = "NMN";
    /** Newline. */
    private static final String NL = "\n";

    /** Array of strings to test names with. */
    private static final String[] TEST_STRINGS = {"", " ", "a", "A", "\0", "x",
                                                  "0123456789",
                                                  "012345678901234567890", NL,
                                                  null};

    /** Name instance for test. */
    private Name name;

    /**
     * Perform test setup.
     */
    @Before
    public void setUp() {
        name = new com.scg.util.Name();
    }

    /**
     * Perform test tear down.
     */
    @After
    public void tearDown() {
        name = null;
    }

    /**
     * Test null constructor.
     */
    @Test
    public void testNullConstructor() {
        final Name testName = new Name();
        assertEquals("Name() constructor failed, on first name",
                     NFN, testName.getFirstName());
        assertEquals("Name() constructor failed, on last name",
                     NLN, testName.getLastName());
        assertEquals("Name() constructor failed, on middle name",
                     NMN, testName .getMiddleName());
    }

    /**
     * Test chained constructor.
     */
    @Test
    public void testChainedConstructor() {
        final Name testName = new Name(CODER, JOHN);
        assertEquals("Name(lastName, firstName) failed, on last name",
                     CODER, testName.getLastName());
        assertEquals("Name(lastName, firstName) failed, on first name",
                     JOHN, testName.getFirstName());
        assertEquals("Name(lastName, firstName) failed, on middle name",
                     NMN, testName.getMiddleName());
    }

    /**
     * Test full constructor.
     */
    @Test
    public void testFullConstructor() {
        final Name testName = new Name(CODER, JOHN, QUINCY);
        assertEquals("Name(lastName, firstName, middleName) failed", QUINCY,
                testName.getMiddleName());
    }

    /**
     * Test setFirstName and getFirstName methods.
     */
    @Test
    public void testSetGetFirstName() {
        for (int i = 0; i < TEST_STRINGS.length; i++) {
            name.setFirstName(TEST_STRINGS[i]);
            assertEquals(TEST_STRINGS[i], name.getFirstName());
        }
    }

    /**
     * Test setLastName and getLastName methods.
     */
    @Test
    public void testSetGetLastName() {
        for (int i = 0; i < TEST_STRINGS.length; i++) {
            name.setLastName(TEST_STRINGS[i]);
            assertEquals(TEST_STRINGS[i], name.getLastName());
        }
    }

    /**
     * Test setMiddleName and getMiddleName methods.
     */
    @Test
    public void testSetGetMiddleName() {
        for (int i = 0; i < TEST_STRINGS.length; i++) {
            name.setMiddleName(TEST_STRINGS[i]);
            assertEquals(TEST_STRINGS[i], name.getMiddleName());
        }
    }

    /**
     * Test print method.
     */
    @Test
    public void testPrint() {
        name = new Name(CODER, JOHN, QUINCY);
        assertEquals("Name.print() failed", "Coder, John Quincy", name
                .toString());
    }

    /**
     * Test equals and hashCode methods.
     */
    @Test
    public void testEqualsAndHashCode() {
        Name testName1 = new Name(CODER, JOHN);
        Name testName2 = new Name(CODER, JOHN, QUINCY);
        final Name nullName = null;
        assertFalse(testName1.equals(testName2));
        assertFalse(testName1.equals(nullName));
        testName1 = new Name(CODER, JOHN, null);
        assertFalse(testName1.equals(testName2));

        testName1 = new Name(CODER, JOHN, QUINCY);
        assertTrue(testName1.equals(testName1));

        // Test hashcode
        testName2 = new Name(CODER, JOHN);
        assertFalse(testName1.hashCode() == testName2.hashCode());
        testName1 = new Name(CODER, JOHN, QUINCY);
        assertTrue(testName1.hashCode() == testName1.hashCode());
    }
}
