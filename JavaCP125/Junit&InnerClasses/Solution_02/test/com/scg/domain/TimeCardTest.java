package com.scg.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.scg.util.Name;

/**
 * JUnit test for the TimeCard class.
 */
public final class TimeCardTest {
    /** Constant for test year. */
    private static final int TEST_YEAR = 2004;
    /** Constant for hours per day. */
    private static final int HOURS_PER_DAY = 8;
    /** Constant for start day. */
    private static final int START_DAY = 6;
    /** Constant for expected total hours. */
    private static final int EXPECTED_TOTAL_HOURS = 16;
    /** Constant for expected total billable hours. */
    private static final int EXPECTED_TOTAL_BILLABLE_HOURS = 16;
    /** Constant for expected total non-billable hours. */
    private static final int EXPECTED_TOTAL_NON_BILLABLE_HOURS = 16;
    /** Constant for expected consulting hours. */
    private static final int EXPECTED_CONSULTING_HOURS = 3;
    /** Constant for expected billable consulting hours. */
    private static final int EXPECTED_BILLABLE_CONSULTING_HOURS = 24;
    /** Constant for expected non-billable consulting hours. */
    private static final int EXPECTED_NON_BILLABLE_CONSULTING_HOURS = 32;
    /** TimeCard for test. */
    private TimeCard timecard;
    /** ClientAccount for test. */
    private ClientAccount client;
    /** Calendar for test. */
    private final Calendar calendar = new GregorianCalendar(TEST_YEAR, Calendar.JANUARY, START_DAY);
    /** Date for test. */
    private Date date;
    /** Date representing next day for test. */
    private Date nextDay;
    /** Consultant for test. */
    private Consultant programmer;

    /**
     * Perform test setup.
     */
    @Before
    public void setUp() {
        client = new ClientAccount("Acme Industries",
                new Name("Contact", "Guy"));
        programmer = new Consultant(new Name("Programmer", "J.", "Random"));
        date = calendar.getTime();
        timecard = new TimeCard(programmer, date);
        final NonBillableAccount nonbillableaccount = NonBillableAccount.VACATION;
        ConsultantTime consultantTime = new ConsultantTime(date, client,
                Skill.SYSTEM_ARCHITECT, HOURS_PER_DAY);
        timecard.addConsultantTime(consultantTime);
        calendar.roll(Calendar.DAY_OF_MONTH, 1);
        nextDay = calendar.getTime();
        consultantTime = new ConsultantTime(date, nonbillableaccount,
                Skill.SYSTEM_ARCHITECT, HOURS_PER_DAY);
        timecard.addConsultantTime(consultantTime);

    }

    /**
     * Perform test tear down.
     */
    @After
    public void tearDown() {
        timecard = null;
    }

    /**
     * Tests the getConsultant method.
     */
    @Test
    public void testGetConsultant() {
        assertNotNull("getConsultant() failed", timecard.getConsultant());
    }

    /**
     * Tests the getTotalBillableHours method.
     */
    @Test
    public void testGetTotalBillableHours() {
        assertEquals(HOURS_PER_DAY, timecard.getTotalBillableHours());
    }

    /**
     * Tests the getTotalNonBillableHours method.
     */
    @Test
    public void testGetTotalNonBillableHours() {
        assertEquals(HOURS_PER_DAY, timecard.getTotalNonBillableHours());
    }

    /**
     * Tests the getConsultingHours method.
     */
    @Test
    public void testGetConsultingHours() {
        assertEquals(2, timecard.getConsultingHours().size());
    }

    /**
     * Tests the getTotalHours method.
     */
    @Test
    public void testGetTotalHours() {
        assertEquals(EXPECTED_TOTAL_HOURS, timecard.getTotalHours());
    }

    /**
     * Tests the getBillableHoursForClient method.
     */
    @Test
    public void testGetBillableHoursForClient() {
        assertEquals(1, timecard.getBillableHoursForClient(client.getName()).size());
    }

    /**
     * Tests the addConsultantTime method.
     */
    @Test
    public void testAddConsultantTime() {
        ConsultantTime consultantTime = new ConsultantTime(date,
                client, Skill.SYSTEM_ARCHITECT, HOURS_PER_DAY);
        timecard.addConsultantTime(consultantTime);
        assertEquals("addConsultantTime() failed to add ConsultantTime",
                     EXPECTED_CONSULTING_HOURS, timecard.getConsultingHours().size());
        assertEquals("addConsultantTime() failed to update totalHours (billable)",
                     EXPECTED_BILLABLE_CONSULTING_HOURS, timecard.getTotalHours());
        assertEquals("addConsultantTime() failed to update totalBillableHours",
                EXPECTED_TOTAL_BILLABLE_HOURS, timecard.getTotalBillableHours());

        consultantTime = new ConsultantTime(nextDay,
                NonBillableAccount.VACATION, Skill.UNKNOWN_SKILL, HOURS_PER_DAY);
        timecard.addConsultantTime(consultantTime);
        assertEquals("addConsultantTime() failed to update nonBillableHours",
                EXPECTED_TOTAL_NON_BILLABLE_HOURS, timecard.getTotalNonBillableHours());
        assertEquals("addConsultantTime() failed to update totalHours (non-billable)",
                     EXPECTED_NON_BILLABLE_CONSULTING_HOURS, timecard.getTotalHours());
    }

    /**
     * Tests the toString method.
     */
    @Test
    public void testToString() {

        ConsultantTime consultantTime = new ConsultantTime(date,
                client, Skill.SOFTWARE_ENGINEER, HOURS_PER_DAY);
        timecard.addConsultantTime(consultantTime);
        consultantTime = new ConsultantTime(nextDay,
                NonBillableAccount.VACATION, Skill.UNKNOWN_SKILL, HOURS_PER_DAY);
        timecard.addConsultantTime(consultantTime);
        assertTrue(timecard.toString() != null && !timecard.toString().isEmpty());
    }
}
