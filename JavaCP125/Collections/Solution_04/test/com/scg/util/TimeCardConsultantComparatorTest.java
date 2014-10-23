package com.scg.util;

import static junit.framework.Assert.assertTrue;

import java.util.Calendar;

import org.junit.Test;

import com.scg.domain.Consultant;
import com.scg.domain.TimeCard;

/**
 * JUnit test for the TimeCardConsultantComparator class.
 *
 * @author Russ Moul
 */
public final class TimeCardConsultantComparatorTest {
    /** String literal for "Coder". */
    private static final String CODER = "Coder";
    /** String literal for "Jane". */
    private static final String JANE = "Jane";
    /** String literal for "John". */
    private static final String JOHN = "John";
    /** String literal for "Q.". */
    private static final String Q_DOT = "Q.";

    /**
     * Tests the compare method.
     */
    @Test
    public void testCompare() {
        final TimeCardConsultantComparator timeCardConsultantComparator = new TimeCardConsultantComparator();
        final Calendar calendar = Calendar.getInstance();

        final TimeCard firstTimeCard = new TimeCard(new Consultant(new Name(CODER,
                JANE, Q_DOT)), calendar.getTime());
        TimeCard secondTimeCard = new TimeCard(new Consultant(new Name(CODER,
                JANE, Q_DOT)), calendar.getTime());

        assertTrue(timeCardConsultantComparator.compare(firstTimeCard, secondTimeCard) == 0);

        secondTimeCard = new TimeCard(new Consultant(new Name(CODER, JOHN,
                Q_DOT)), calendar.getTime());
        assertTrue(timeCardConsultantComparator.compare(firstTimeCard, secondTimeCard) < 0);
        assertTrue(timeCardConsultantComparator.compare(secondTimeCard, firstTimeCard) > 0);
    }
}
