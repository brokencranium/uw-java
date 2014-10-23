package com.scg.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

/**
 * Encapsulates a range of two dates, inclusive of the start date and end date.
 *
 * @author Russ Moul
 */
public final class DateRange {

    /** The start date of this DateRange. The date is included in the range. */
    private Date startDate;

    /** The end date for this DateRange. The date is included in the range. */
    private Date endDate;

    /**
     * Construct a DateRange given two dates.
     *
     * @param startDate
     *            the start date for this DateRange.
     * @param endDate
     *            the end date for this DateRange.
     */
    public DateRange(final Date startDate, final Date endDate) {
        init(startDate, endDate);
    }

    /**
     * Construct a DateRange given two date strings in the correct format.
     *
     * @param start String representing the start date, of the form MM/dd/yyyy.
     * @param end String representing the end date, of the form MM/dd/yyyy.
     *
     */
    public DateRange(final String start, final String end) {
        Date tempStartDate = null;
        Date tempEndDate = null;
        try {
            final DateFormat dateFormatter = DateFormat.getDateInstance(DateFormat.SHORT);
            dateFormatter.setLenient(true);

            tempStartDate = dateFormatter.parse(start);
            tempEndDate = dateFormatter.parse(end);
        } catch (final ParseException pe) {
            throw new IllegalArgumentException("Invalid date format(s)", pe);
        }

        init(tempStartDate, tempEndDate);
    }

    /**
     * Construct a DateRange for the given month.
     *
     * @param month the month for which this DateRange should be constructed.
     *              This integer constant should be obtained from the
     *              java.util.Calendar class by month name, e.g. Calendar.JANUARY.
     * @param year the calendar year
     */
    public DateRange(final int month, final int year) {
        final Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.DATE, calendar.getActualMinimum(Calendar.DATE));
        final Date tempStartDate = calendar.getTime();

        calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DATE));
        final Date tempEndDate = calendar.getTime();

        init(tempStartDate, tempEndDate);
    }

    /**
     * Common initialization used by all constructors.
     *
     * @param sDate the start date
     * @param eDate the end date
     */
    private void init(final Date sDate, final Date eDate) {
        final Calendar cal = Calendar.getInstance();
        cal.setTime(sDate);
        setMinimumTime(cal);
        this.startDate = cal.getTime();

        cal.setTime(eDate);
        setMaximumTime(cal);
        this.endDate = cal.getTime();

        if (!(startDate.before(endDate))) {
            throw new IllegalArgumentException(
                    "Start date must be before end date.");
        }
    }

    /**
     * Returns the start date for this DateRange.
     *
     * @return the start date for this DateRange.
     */
    public Date getStartDate() {
        return new Date(startDate.getTime());
    }

    /**
     * Returns the end date for this DateRange.
     *
     * @return the end date for this DateRange.
     */
    public Date getEndDate() {
        return new Date(endDate.getTime());
    }

    /**
     * Returns true if the specified date is within the range start date <= date <=
     * end date.
     *
     * @param date
     *            the date to check for being within this DateRange.
     * @return true if the specified date is within this DateRange.
     */
    public boolean isInRange(final Date date) {
        return !((date.before(startDate)) || (date.after(endDate)));
    }

    /**
     * Convenience method to set the clock time of the calendar attribute object
     * to the minimum time of day.
     *
     * @param calendar the calendar to set the min time on
     */
    private void setMinimumTime(final Calendar calendar) {
        calendar.set(Calendar.HOUR_OF_DAY,
                     calendar.getActualMinimum(Calendar.HOUR_OF_DAY));
        calendar.set(Calendar.MINUTE,
                     calendar.getActualMinimum(Calendar.MINUTE));
        calendar.set(Calendar.SECOND,
                     calendar.getActualMinimum(Calendar.SECOND));
        calendar.set(Calendar.MILLISECOND,
                     calendar.getActualMinimum(Calendar.MILLISECOND));
    }

    /**
     * Convenience method to set the calendar attribute object to the maximum
     * time of day.
     *
     * @param calendar the calendar to set the max time on
     */
    private void setMaximumTime(final Calendar calendar) {
        calendar.set(Calendar.HOUR_OF_DAY,
                     calendar.getActualMaximum(Calendar.HOUR_OF_DAY));
        calendar.set(Calendar.MINUTE,
                     calendar.getActualMaximum(Calendar.MINUTE));
        calendar.set(Calendar.SECOND,
                     calendar.getActualMaximum(Calendar.SECOND));
        calendar.set(Calendar.MILLISECOND,
                     calendar.getActualMaximum(Calendar.MILLISECOND));
    }
}
