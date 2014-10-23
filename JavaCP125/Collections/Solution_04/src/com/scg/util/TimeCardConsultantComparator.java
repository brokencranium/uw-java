package com.scg.util;

import java.util.Comparator;

import com.scg.domain.TimeCard;

/**
 * Compares two TimeCard objects by ascending date order, consultant, totalHours,
 * totalBillableHours and totalNonBillableHours.
 *
 * @author Russ Moul
 */
public final class TimeCardConsultantComparator implements Comparator<TimeCard> {

    /**
     * Compares its two arguments for order.  Returns a negative integer,
     * zero, or a positive integer as the first argument is less than, equal
     * to, or greater than the second.<p>
     *
     *
     * @param firstTimeCard the first object to be compared.
     * @param secondTimeCard the second object to be compared.
     * @return a negative integer, zero, or a positive integer as the
     *         first argument is less than, equal to, or greater than the
     *         second.
     */
    @Override
    public int compare(final TimeCard firstTimeCard, final TimeCard secondTimeCard) {
        final int BEFORE = -1;
        final int EQUAL = 0;
        final int AFTER = 1;

        if (firstTimeCard == secondTimeCard) return EQUAL;
        
        int diff = firstTimeCard.getConsultant().compareTo(secondTimeCard.getConsultant());
        if (diff != 0) return diff;

        diff = firstTimeCard.getWeekStartingDay().compareTo(secondTimeCard.getWeekStartingDay());
        if (diff != 0) return diff;
        
        if (firstTimeCard.getTotalHours() < secondTimeCard.getTotalHours()) return BEFORE;
        if (firstTimeCard.getTotalHours() > secondTimeCard.getTotalHours()) return AFTER;

        if (firstTimeCard.getTotalBillableHours() < secondTimeCard.getTotalBillableHours()) return BEFORE;
        if (firstTimeCard.getTotalBillableHours() > secondTimeCard.getTotalBillableHours()) return AFTER;

        if (firstTimeCard.getTotalNonBillableHours() < secondTimeCard.getTotalNonBillableHours()) return BEFORE;
        if (firstTimeCard.getTotalNonBillableHours() > secondTimeCard.getTotalNonBillableHours()) return AFTER;

        // At this point we could walk through the list and compare the individual
        // ConsultantTime entries, but, its pretty expensivesvo we skip it.

        return EQUAL;
    }
}
