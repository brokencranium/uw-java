package test.scg.util;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;

import java.util.Calendar;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import com.scg.util.DateRange;

/**
 * JUnit test for the DateRange class.
 *
 * @author Russ Moul
 */
public final class DateRangeTest {
    /** Test month. */
    private static final int TEST_MONTH = Calendar.MARCH;

    /** Test year. */
    private static final int TEST_YEAR = 2006;

    /** Test start date. */
    private Date startDate;

    /** Test end date. */
    private Date endDate;

    /**
     * Test the various constructors.
     */
    @Test
    public void testConstructors() {
        DateRange dateRange = new DateRange(startDate, endDate);
        assertNotNull("DateRange(Date, Date) failed.", dateRange);
        assertEquals(startDate, dateRange.getStartDate());
        assertEquals(endDate, dateRange.getEndDate());

        dateRange = new DateRange(TEST_MONTH, TEST_YEAR);
        assertEquals(startDate, dateRange.getStartDate());
        assertEquals(endDate, dateRange.getEndDate());
    }

    /**
     * Initialize start and end month dates.
     */
    @Before
    public void setUp() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(TEST_YEAR, TEST_MONTH, calendar
                .getMinimum(Calendar.DAY_OF_MONTH), 0, 0, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        startDate = calendar.getTime();
        calendar.set(TEST_YEAR, TEST_MONTH,
                     calendar.getMaximum(Calendar.DAY_OF_MONTH),
                     calendar.getMaximum(Calendar.HOUR_OF_DAY),
                     calendar.getMaximum(Calendar.MINUTE),
                     calendar.getMaximum(Calendar.SECOND));
        calendar.set(Calendar.MILLISECOND,
                     calendar.getMaximum(Calendar.MILLISECOND));
        endDate = calendar.getTime();
    }

    /**
     * Test the isInDange method.
     */
    @Test
    public void testIsInRange() {
        DateRange dateRange = new DateRange(startDate, endDate);
        assertTrue(dateRange.isInRange(startDate));

        DateRange testDateRange = new DateRange(Calendar.FEBRUARY, TEST_YEAR);
        assertFalse(testDateRange.isInRange(startDate));

        testDateRange = new DateRange("2/1/2006", "2/28/2006");
        assertFalse(testDateRange.isInRange(startDate));

        testDateRange = new DateRange("2/26/2006", "3/04/2006");
        assertTrue(testDateRange.isInRange(startDate));
    }
}
