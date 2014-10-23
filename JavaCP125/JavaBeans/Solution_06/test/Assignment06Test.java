import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.VetoableChangeListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.scg.domain.BenefitManager;
import com.scg.domain.CompensationManager;
import com.scg.domain.Eeoc;
import com.scg.domain.HumanResourceManager;
import com.scg.domain.StaffConsultant;
import com.scg.util.Name;

/**
 * JUnit test for the change events and listeners.
 *
 * @author Russ Moul
 */
public final class Assignment06Test {
    /** Initial pay rate for coder. */
    private static final int CODER_INITIAL_PAY_RATE = 9524;

    /** Initial pay rate for architect. */
    private static final int ARCHITECT_INITIAL_PAY_RATE = 10000;

    /** Initial pay rate for tester. */
    private static final int TESTER_INITIAL_PAY_RATE = 5000;

    /** Initial pay rate for engineer. */
    private static final int ENGINEER_INITIAL_PAY_RATE = 7500;

    /** Initial value assigned to sick leave hours for all consultants. */
    private static final int INITIAL_SICK_LEAVE_HOURS = 80;

    /** Test value for sick leave hours update. */
    private static final int TEST_SICK_LEAVE_HOURS = 320;

    /** Initial value assigned to vacation hours for all consultants. */
    private static final int INITIAL_VACATION_HOURS = 40;

    /** Test value for vacation hours update. */
    private static final int TEST_VACATION_HOURS = 240;

    /** Test value for valid pay rate adjustment. */
    private static final int VALID_RAISE = 10000;

    /** Test value for invalid pay rate adjustment. */
    private static final int INVALID_RAISE = 10501;

    /** Property name for sickLeaveHours. */
    private static final String SICK_LEAVE_HOURS_PROP = "sickLeaveHours";

    /** Property name for vacationHours. */
    private static final String VACATION_HOURS_PROP = "vacationHours";

    /** Property name for payRate. */
    private static final String PAY_RATE_PROP = "payRate";

    /** Consultants for testing. */
    private List<StaffConsultant> consultantList;

    /** A specific consultant for testing. */
    private StaffConsultant staffConsultant;

    /** HR object for testing. */
    private HumanResourceManager hrServer;

    /** EEOC object for testing. */
    private Eeoc watchDog;

    /** property listener for testing. */
    private TestListener testListener;

    /** Property listener for testing purposes, keeps track of the last event. */
    static class TestListener implements PropertyChangeListener, VetoableChangeListener {
        /** The last event. */
        private PropertyChangeEvent lastEvent;

        /**
         * Gets the last event and clears it.
         *
         * @return the last event, null if an event has not arrived since last called.
         */
        public PropertyChangeEvent lastEvent() {
            final PropertyChangeEvent tmp = lastEvent;
            lastEvent = null;
            return tmp;
        }

        /**
         * Simply records the event.
         *
         * @param event the change event
         */
        @Override
        public void propertyChange(final PropertyChangeEvent event) {
            this.lastEvent = event;
        }

        /**
         * Simply records the event.
         *
         * @param event the change event
         */
        @Override
        public void vetoableChange(final PropertyChangeEvent event) {
            this.lastEvent = event;
        }
    }

    /**
     * Initialize all the objects used for testing.
     */
    @Before
    public void setUp() {
        // Create some Consultants
        consultantList = new ArrayList<StaffConsultant>();
        staffConsultant = new StaffConsultant(
                          new Name("Coder", "Kalvin"), CODER_INITIAL_PAY_RATE,
                          INITIAL_SICK_LEAVE_HOURS, INITIAL_VACATION_HOURS);
        consultantList.add(staffConsultant);
        consultantList.add(new StaffConsultant(
                           new Name("Architect", "Amber", "K."), ARCHITECT_INITIAL_PAY_RATE,
                           INITIAL_SICK_LEAVE_HOURS, INITIAL_VACATION_HOURS));
        consultantList.add(new StaffConsultant(
                           new Name("Tester", "Teddy", "B."), TESTER_INITIAL_PAY_RATE,
                           INITIAL_SICK_LEAVE_HOURS, INITIAL_VACATION_HOURS));
        consultantList.add(new StaffConsultant(
                           new Name("Engineer", "Ernie"), ENGINEER_INITIAL_PAY_RATE,
                           INITIAL_SICK_LEAVE_HOURS, INITIAL_VACATION_HOURS));

        // create the server
        hrServer = new HumanResourceManager();
        watchDog = new Eeoc();
        hrServer.addTerminationListener(watchDog);

        final CompensationManager compMgr = new CompensationManager();
        final BenefitManager bm = new BenefitManager();

        testListener = new TestListener();

        for (StaffConsultant sc : consultantList) {
            sc.addVetoableChangeListener(compMgr);
            sc.addPayRateListener(compMgr);
            sc.addSickLeaveHoursListener(bm);
            sc.addVacationHoursListener(bm);
            sc.addPropertyChangeListener(testListener);
        }
    }

    /** Test the vetoable property (payRate) */
    @Test
    public void testVeto() {
        hrServer.adjustPayRate(staffConsultant, VALID_RAISE);
        assertEquals(VALID_RAISE, staffConsultant.getPayRate());
        PropertyChangeEvent event = testListener.lastEvent();
        assertEquals(PAY_RATE_PROP, event.getPropertyName());
        assertEquals(VALID_RAISE, event.getNewValue());
        assertEquals(staffConsultant, event.getSource());

        // this should be vetoed, so the pay rate won't be set
        hrServer.adjustPayRate(staffConsultant, INVALID_RAISE);
        assertEquals(VALID_RAISE, staffConsultant.getPayRate());
        event = testListener.lastEvent();
        assertNull(event);
    }

    /** Test the termination events */
    @Test
    public void testTerminations() {
        assertEquals(0, watchDog.voluntaryTerminationCount());
        assertEquals(0, watchDog.forcedTerminationCount());

        // Terminate two employees
        final Iterator<StaffConsultant> iter = consultantList.iterator();
        if (iter.hasNext()) {
            final StaffConsultant consultant = iter.next();
            hrServer.acceptResignation(consultant);
        }

        assertEquals(1, watchDog.voluntaryTerminationCount());
        if (iter.hasNext()) {
            final StaffConsultant consultant = iter.next();
            hrServer.terminate(consultant);
        }
        assertEquals(1, watchDog.forcedTerminationCount());

    }

    /** Test the simple properties (sickLeaveHours and vacationHours) */
    @Test
    public void testBenefits() {
        hrServer.adjustSickLeaveHours(staffConsultant, TEST_SICK_LEAVE_HOURS);
        PropertyChangeEvent event = testListener.lastEvent();
        assertEquals(SICK_LEAVE_HOURS_PROP, event.getPropertyName());
        assertEquals(TEST_SICK_LEAVE_HOURS, event.getNewValue());
        assertEquals(staffConsultant, event.getSource());

        hrServer.adjustVacationHours(staffConsultant, TEST_VACATION_HOURS);
        event = testListener.lastEvent();
        assertEquals(VACATION_HOURS_PROP, event.getPropertyName());
        assertEquals(TEST_VACATION_HOURS, event.getNewValue());
        assertEquals(staffConsultant, event.getSource());

    }
}
