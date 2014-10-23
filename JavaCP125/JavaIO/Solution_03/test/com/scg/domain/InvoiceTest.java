package com.scg.domain;

import static org.junit.Assert.assertEquals;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.junit.Before;
import org.junit.Test;

import com.scg.util.Address;
import com.scg.util.Name;
import com.scg.util.StateCode;

/**
 * JUnit test for Invoice class.
 */

public final class InvoiceTest {
    /** String constant for "FooBar Enterprises". */
    private static final String FOOBAR = "FooBar.com";
    /** String constant for "Client". */
    private static final String CLIENT = "Client";
    /** String constant for "J.". */
    private static final String J_DOT = "J.";
    /** String constant for "Random". */
    private static final String RANDOM = "Random";
    /** String constant for "Coder". */
    private static final String CODER = "Coder";
    /** String constant for "Carl". */
    private static final String CARL = "Carl";
    /** String constant for street address. */
    private static final String STREET = "1024 Kilobyte Dr.";
    /** String constant for city. */
    private static final String CITY = "Silicone Gulch";
    /** String constant for ZIP code. */
    private static final String ZIP = "94105";

    /** Number of hours in a standard working day. */
    private static final int HOURS_PER_DAY = 8;

    /** The first Monday of the test month. */
    private static final int TEST_START_FIRST_WEEK = 6;

    /** Expected total hours. */
    private static final int TOTAL_HOURS = 32;

    /** Expected total charges. */
    private static final int TOTAL_CHARGES = 4800;

    
    /** The test year. */
    private static final int TEST_YEAR = 2006;

    /** Invoice instance for test. */
    private Invoice invoice;

    /** Consultant instance for test. */
    private Consultant programmer;

    /** TimeCard instance for test. */
    private TimeCard timecard;

    /** ClientAccount instance for test. */
    private ClientAccount client;

    /** Calendar instance for test. */
    private Calendar calendar;

    /** Current date for test. */
    private Date currentDate;

    /** Expected date for test. */
    private Date expectedDate;

    /** NonBillableAccount instance for test. */
    private NonBillableAccount vacation;

    /**
     * Perform test setup.
     */
    @Before
    public void setUp() {

        client = new ClientAccount(FOOBAR, new Name(CLIENT, J_DOT, RANDOM),
                                   new Address(STREET, CITY,
                                   StateCode.CA, ZIP));

        invoice = new Invoice(client, Calendar.MARCH, TEST_YEAR);
        programmer = new Consultant(new Name(CODER, CARL));
        vacation = NonBillableAccount.VACATION;
        calendar = Calendar.getInstance();

        calendar = new GregorianCalendar(TEST_YEAR, Calendar.MARCH,
                TEST_START_FIRST_WEEK);
        currentDate = calendar.getTime();
        timecard = new TimeCard(programmer, currentDate);
        timecard.addConsultantTime(new ConsultantTime(currentDate, client,
                Skill.SOFTWARE_ENGINEER, HOURS_PER_DAY));
        calendar.roll(Calendar.DATE, 1);
        currentDate = calendar.getTime();

        timecard.addConsultantTime(new ConsultantTime(currentDate, client,
                Skill.SOFTWARE_ENGINEER, HOURS_PER_DAY));
        calendar.roll(Calendar.DATE, 1);
        currentDate = calendar.getTime();
        timecard.addConsultantTime(new ConsultantTime(currentDate, client,
                Skill.SOFTWARE_ENGINEER, HOURS_PER_DAY));
        calendar.roll(Calendar.DATE, 1);
        currentDate = calendar.getTime();
        timecard.addConsultantTime(new ConsultantTime(currentDate, client,
                Skill.SOFTWARE_ENGINEER, HOURS_PER_DAY));
        calendar.roll(Calendar.DATE, 1);
        currentDate = calendar.getTime();
        timecard.addConsultantTime(new ConsultantTime(currentDate, vacation,
                Skill.SOFTWARE_ENGINEER, HOURS_PER_DAY));
        // Add a ConsultantTime that is out of the date range
        calendar.roll(Calendar.MONTH, 1);
        currentDate = calendar.getTime();
        timecard.addConsultantTime(new ConsultantTime(currentDate, client,
                Skill.SOFTWARE_ENGINEER, HOURS_PER_DAY));
        invoice.extractLineItems(timecard);

        calendar = Calendar.getInstance();
    }

    /**
     * Test the getStartDate method.
     */
    @Test
    public void testGetStartDate() {
        calendar.set(TEST_YEAR, Calendar.MARCH,
                     calendar.getMinimum(Calendar.DAY_OF_MONTH),
                     calendar.getMinimum(Calendar.HOUR_OF_DAY),
                     calendar.getMinimum(Calendar.MINUTE),
                     calendar.getMinimum(Calendar.SECOND));
        calendar.set(Calendar.MILLISECOND, calendar.getMinimum(Calendar.MILLISECOND));
        expectedDate = calendar.getTime();

        assertEquals("getStartDate() failed: ", expectedDate,
                invoice.getStartDate());
    }

    /**
     * Test the getEndDate method.
     */
    @Test
    public void testGetEndDate() {
        calendar.set(TEST_YEAR, Calendar.MARCH, calendar
                .getMaximum(Calendar.DAY_OF_MONTH), calendar
                .getMaximum(Calendar.HOUR_OF_DAY), calendar
                .getMaximum(Calendar.MINUTE), calendar
                .getMaximum(Calendar.SECOND));
        calendar.set(Calendar.MILLISECOND, calendar.getMaximum(Calendar.MILLISECOND));
        expectedDate = calendar.getTime();
        assertEquals(expectedDate, invoice.getEndDate());
        assertEquals("getEndDate() failed: ", expectedDate, invoice.getEndDate());
    }

    /**
     * Test the getInvoiceMonth method.
     */
    @Test
    public void testGetInvoiceMonth() {
        assertEquals("getInvoiceMonth() failed: ", Calendar.MARCH, invoice
                .getInvoiceMonth());
    }

    /**
     * Test the getTotalHours method.
     */
    @Test
    public void testGetTotalHours() {
        assertEquals("getTotalHours() failed: ", TOTAL_HOURS, invoice.getTotalHours());
    }

    /**
     * Test the gettotalCharges method.
     */
    @Test
    public void testGetTotalCharges() {
        assertEquals("getTotalCharges() failed: ", TOTAL_CHARGES, invoice.getTotalCharges());
    }
/**
    @Test
    public void testAddLineItem() throws Exception {
        calendar.set(Calendar.DAY_OF_MONTH, 10);
        Date date = calendar.getTime();
        InvoiceLineItem item = new InvoiceLineItem(date, programmer,
                Skill.SOFTWARE_ENGINEER, HOURS_PER_DAY);
        invoice.addLineItem(item);
        assertEquals("addLineItem failed: ", 40, invoice.getTotalHours());
        assertEquals("addLineItem failed: ", 6000, invoice.getTotalCharges());
    }
    */
}
