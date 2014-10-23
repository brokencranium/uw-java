package test.scg.util;

import static junit.framework.Assert.assertTrue;

import java.util.Calendar;

import org.junit.Test;

import com.scg.domain.Consultant;
import com.scg.domain.TimeCard;
import com.scg.util.Name;
import com.scg.util.TimeCardConsultantComparator;

/**
 * JUnit test for the TimeCardConsultantComparator class.
 *
 * @author Russ Moul
 */
public final class TimeCardConsultantComparatorTest {

    /**
     * Tests the compare method.
     */
    @Test
    public void testCompare() {
        TimeCardConsultantComparator timeCardConsultantComparator = new TimeCardConsultantComparator();
        Calendar calendar = Calendar.getInstance();

        TimeCard firstTimeCard = new TimeCard(new Consultant(new Name("Coder",
                "Jane", "Q.")), calendar.getTime());
        TimeCard secondTimeCard = new TimeCard(new Consultant(new Name("Coder",
                "Jane", "Q.")), calendar.getTime());

        assertTrue(timeCardConsultantComparator.compare(firstTimeCard, secondTimeCard) == 0);

        secondTimeCard = new TimeCard(new Consultant(new Name("Coder", "John",
                "Q.")), calendar.getTime());
        assertTrue(timeCardConsultantComparator.compare(firstTimeCard, secondTimeCard) < 0);
        assertTrue(timeCardConsultantComparator.compare(secondTimeCard, firstTimeCard) > 0);
    }
}
