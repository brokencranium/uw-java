package com.scg.util;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import com.scg.domain.Consultant;
import com.scg.domain.TimeCard;

/**
 * Utility class for processing <code>TimeCard</code> lists.
 *
 * @author Russ Moul
 */
public final class TimeCardListUtil {
    /** Days per week. */
    private static final int DAYS_PER_WEEK = 6;

    /** Comparator used by his class. */
    private static TimeCardConsultantComparator consultantComparator =
               new TimeCardConsultantComparator();

    /**
     * Prevent instantiation.
     */
    private TimeCardListUtil() {
    }

    /**
     * Sorts this list into ascending order, by the start date.
     *
     * @param timeCards the list of time cards to sort
     */
    public static void sortByStartDate(final List<TimeCard> timeCards) {
        Collections.sort(timeCards);
    }

    /**
     * Sorts this list into ascending order by consultant name..
     *
     * @param timeCards the list of time cards to sort
     */
    public static void sortByConsultantName(final List<TimeCard> timeCards) {
        Collections.sort(timeCards, consultantComparator);
    }

    /**
     * Get a list of TimeCards that cover dates that fall within a date range.
     * Each time may have time entries through out one week beginning with the
     * time card start date.
     *
     * @param timeCards the list of time cards to extract the sub set from
     * @param dateRange The DateRange within which the dates of the returned
     *                  TimeCards must fall.
     *
     * @return a list of TimeCards having  dates fall within the date range.
     */
    public static List<TimeCard> getTimeCardsForDateRange(
                                     final List<TimeCard> timeCards,
                                     final DateRange dateRange) {
        final List<TimeCard> returnList = new ArrayList<TimeCard>();
        for (final TimeCard currentTimeCard : timeCards) {
            // Check the first day of the week and the last
            final Date startDate = currentTimeCard.getWeekStartingDay();
            final Calendar cal = Calendar.getInstance();
            cal.setTime(startDate);
            cal.add(Calendar.DATE, DAYS_PER_WEEK);
            final Date endDate = cal.getTime();

            if (dateRange.isInRange(startDate) ||
                dateRange.isInRange(endDate)) {
                returnList.add(currentTimeCard);
            }
        }
        return returnList;
    }

    /**
     * Get a list of TimeCards for the specified consultant.
     *
     * @param timeCards the list of time cards to extract the sub set from
     * @param consultant the consultant whose TimeCards will be obtained.
     *
     * @return a list of TimeCards for the specified consultant.
     */
    public static List<TimeCard> getTimeCardsForConsultant(
                                     final List<TimeCard> timeCards,
                                     final Consultant consultant) {
        final List<TimeCard> returnList = new ArrayList<TimeCard>();
        for (final TimeCard currentTimeCard : timeCards) {
            if (currentTimeCard.getConsultant().equals(consultant)) {
                returnList.add(currentTimeCard);
            }
        }
        return returnList;
    }
}
