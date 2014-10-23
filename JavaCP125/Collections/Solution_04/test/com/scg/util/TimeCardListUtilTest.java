package com.scg.util;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.scg.domain.Consultant;
import com.scg.domain.TimeCard;

/**
 * JUnit test for TimeCardListUtil class.
 */
public final class TimeCardListUtilTest {
    /** The test year. */
    private static final int TEST_YEAR = 2007;
    
    /** Constant for the 4th. */
    private static final int DAY_4 = 4;

    /** Constant for the 11th. */
    private static final int DAY_11 = 11;

    /** Constant for the 18th. */
    private static final int DAY_18 = 18;

    /** Test programmer. */
    private Consultant programmer;
    /** Test time card 1. */

    private TimeCard timeCard1;

    /** Test time card 2. */
    private TimeCard timeCard2;

    /** Test time card 3. */
    private TimeCard timeCard3;

    /** Test time cards. */
    private ArrayList<TimeCard> timeCards;

    /** Set up the test fixture. */
    @Before
    public void setUp() {
        final Calendar calendar = Calendar.getInstance();
        calendar.set(TEST_YEAR, Calendar.FEBRUARY, DAY_4, 2, 2, 0);
        Date startDate = calendar.getTime();

        programmer = new Consultant(new Name("Coder", "Carl"));
        final Consultant systemAnalyst = new Consultant(new Name("Architect", "Ann", "S."));

        timeCard1 = new TimeCard(programmer, startDate);

        calendar.set(TEST_YEAR, Calendar.FEBRUARY, DAY_18, 2, 2, 0);
        startDate = calendar.getTime();
        timeCard2 = new TimeCard(systemAnalyst, startDate);

        calendar.set(TEST_YEAR, Calendar.FEBRUARY, DAY_11, 2, 2, 0);
        startDate = calendar.getTime();
        timeCard3 = new TimeCard(programmer, startDate);

        timeCards = new ArrayList<TimeCard>();
        timeCards.add(timeCard1);
        timeCards.add(timeCard2);
        timeCards.add(timeCard3);
    }

    /** Test for the sortByStartDate method. */
    @Test
    public void testSortByStartDate() {
        TimeCardListUtil.sortByStartDate(timeCards);
        assertEquals(timeCard1, timeCards.get(0));
        assertEquals(timeCard3, timeCards.get(1));
        assertEquals(timeCard2, timeCards.get(2));
    }

    /** Test for the sortByConsultantName method. */
    @Test
    public void testSortByConsultantName() {
        TimeCardListUtil.sortByConsultantName(timeCards);
        assertEquals(timeCard2, timeCards.get(0));
        assertEquals(timeCard1, timeCards.get(1));
        assertEquals(timeCard3, timeCards.get(2));

    }

    /** Test for the getTimeCardsForDateRange method. */
    @Test
    public void testGetTimeCardsForDateRange() {
        final DateRange dateRange = new DateRange("02/11/2007", "02/17/2007");

        final List<TimeCard> selected = TimeCardListUtil.getTimeCardsForDateRange(timeCards, dateRange);

        assertEquals(1, selected.size());
    }

    /** Test for the getTimeCardsForConsultant method. */
    @Test
    public void testGetTimeCardsForConsultant() {
        final List<TimeCard> selected = TimeCardListUtil.getTimeCardsForConsultant(timeCards, programmer);
        assertEquals(2, selected.size());
        assertEquals(timeCard1, selected.get(0));
        assertEquals(timeCard3, selected.get(1));
    }
}
