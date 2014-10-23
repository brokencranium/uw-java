package com.scg.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.scg.util.Name;

/**
 * JUnit test for the ConsultantTime class.
 */
public final class ConsultantTimeTest {
    /** Constant for test year. */
    private static final int TEST_YEAR = 2004;
    /** Constant for start day. */
    private static final int START_DAY = 5;
    /** Constant for hours per day. */
    private static final int HOURS_PER_DAY = 8;
    /** String constant for "FooBar, Inc.". */
    private static final String FOOBAR_INC = "FooBar, Inc.";
    /** String constant for "Mouse". */
    private static final String MOUSE = "Mouse";
    /** String constant for "Mickey". */
    private static final String MICKEY = "Mickey";
    
    /** Error message if an IllegalArgumentException isn't caught. */
    private static final String FAILED_ILLEGAL_ARG_EX_MSG = "Failed to throw IllegalArgumentException.";
    /** ConsultantTime instance for test. */
    private ConsultantTime consultanttime;

    /** Calendar instance for test. */
    private final Calendar calendar = new GregorianCalendar(TEST_YEAR, Calendar.JANUARY, START_DAY);

    /** Date instance for test. */
    private Date date;

    /** ClientAccount instance for test. */
    private final ClientAccount client = new ClientAccount(FOOBAR_INC, new Name(MOUSE, MICKEY));

    /**
     * Perform test setup.
     */
    @Before
    public void setUp() {
        date = calendar.getTime();
        consultanttime = new ConsultantTime(date, client, Skill.PROJECT_MANAGER, HOURS_PER_DAY);
    }

    /**
     * Perform test tear down.
     */
    @After
    public void tearDown() {
        consultanttime = null;
    }

    /**
     * Test constructor.
     */
    @Test
    public void testConstructor() {

        try {
            // Test the assertion that hours must be positive.
            consultanttime = new ConsultantTime(date,
                    NonBillableAccount.SICK_LEAVE, Skill.UNKNOWN_SKILL, -HOURS_PER_DAY);
            fail(FAILED_ILLEGAL_ARG_EX_MSG);
        } catch (final IllegalArgumentException ex) {
            System.out.println("Expected exception caught");
            // ex.printStackTrace();
        }
    }

    /**
     * Tests the getDate and setDate methods.
     */
    @Test
    public void testSetGetDate() {
        final Date[] tests = {new Date(), new Date(0), null};

        for (int i = 0; i < tests.length; i++) {
            consultanttime.setDate(tests[i]);
            assertEquals(tests[i], consultanttime.getDate());
        }
    }

    /**
     * Tests the getAccount and setAccount methods.
     */
    @Test
    public void testSetGetAccount() {
        final Account[] tests = {client, null};

        for (int i = 0; i < tests.length; i++) {
            consultanttime.setAccount(tests[i]);
            assertEquals(tests[i], consultanttime.getAccount());
        }
    }

    /**
     * Tests the isBillable method.
     */
    @Test
    public void testIsBillable() {
        consultanttime = new ConsultantTime(date, client,
                                            Skill.PROJECT_MANAGER, HOURS_PER_DAY);
        assertTrue(consultanttime != null);
        assertTrue(consultanttime.isBillable());
        // Test a non-billable account
        consultanttime = new ConsultantTime(date, NonBillableAccount.SICK_LEAVE,
                                            Skill.UNKNOWN_SKILL, HOURS_PER_DAY);
        assertTrue(consultanttime != null);
        assertFalse(consultanttime.isBillable());
    }

    /**
     * Tests the getHours and setHours methods.
     */
    @Test
    public void testSetGetHours() {
        int[] tests = new int[] {Integer.MIN_VALUE, 0, -HOURS_PER_DAY};
        try {
            // Test the assertion that hours must be positive.
            consultanttime.setHours(-HOURS_PER_DAY);
            fail(FAILED_ILLEGAL_ARG_EX_MSG);
        } catch (final IllegalArgumentException ex) {
            System.out.println("Caught expected exception.");
            // ex.printStackTrace();
        }

        tests = new int[] {1, Integer.MAX_VALUE};
        for (int i = 0; i < tests.length; i++) {
            consultanttime.setHours(tests[i]);
            assertEquals(tests[i], consultanttime.getHours());

        }
    }

    /**
     * Tests the getSkill method.
     */
    @Test
    public void testGetSkill() {
        assertEquals(Skill.PROJECT_MANAGER, consultanttime.getSkill());
    }

    /**
     * Tests the toString method.
     */
    @Test
    public void testToString() {
        consultanttime = new ConsultantTime(date,
                new ClientAccount(FOOBAR_INC, new Name(MOUSE, MICKEY)),
                Skill.SOFTWARE_ENGINEER, HOURS_PER_DAY);
        assertTrue(consultanttime.toString() != null && !consultanttime.toString().isEmpty());
    }
}
