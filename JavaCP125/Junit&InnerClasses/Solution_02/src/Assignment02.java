import java.io.PrintStream;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.scg.domain.ClientAccount;
import com.scg.domain.Consultant;
import com.scg.domain.ConsultantTime;
import com.scg.domain.NonBillableAccount;
import com.scg.domain.Skill;
import com.scg.domain.TimeCard;
import com.scg.util.Name;

/**
 * Assignment 02 application.
 */
public final class Assignment02 {

    /** Number of hours in a standard working day. */
    private static final int STD_WORK_DAY = 8;

    /** Some overtime hours. */
    private static final int OT_HOURS = 4;

    /** The start month for our test cases. */
    private static final int TEST_MONTH = Calendar.FEBRUARY;

    /** The first Monday of the test month. */
    private static final int TEST_START_FIRST_WEEK = 27;

    /** The days per week. */
    private static final int DAYS_PER_WEEK = 7;

    /** The test year. */
    private static final int TEST_YEAR = 2006;

    /** The test year. */
    private static final int NUMBER_OF_TIMECARDS = 4;

    /** Index to the first client. */
    private static final int FIRST_CLIENT_NDX = 0;

    /** Index to the second client. */
    private static final int SECOND_CLIENT_NDX = 1;

    /**
     * This class' logger.
     */
    private static final Logger logger = Logger.getLogger("Assignment02");

    /**
     * Prevent instantiation.
     */
    private Assignment02() {
    }

    /**
     * Create some client account instances.
     *
     * @return the created client accounts
     */
    private static ClientAccount[] createClientAccounts() {
        final ClientAccount[] accounts = {
            new ClientAccount("Acme Industries",
                    new Name("Coyote", "Wiley")),
            new ClientAccount("FooBar Enterprises",
                    new Name("Sam", "Yosemite"))
        };
        return accounts;
    }

    /**
     * Create some time card instances.  Also, creates the consultants the
     * time cards are for.
     *
     * @param clients the clients to create time cards for
     * @param month the month to create the time cards for
     * @param startDay the first day on the time card
     * @param year the year for the invoice
     *
     * @return the created time cards
     */
    private static TimeCard[] createTimeCards(final ClientAccount[] clients,
                                             final int month, final int startDay,
                                             final int year) {
        int cardNdx = 0;
        // Create some Consultants
        final Consultant programmer = new Consultant(new Name("Coder", "Carl"));
        final Consultant systemAnalyst = new Consultant(new Name("Architect", "Ann", "S."));

        final TimeCard[] timeCards = new TimeCard[NUMBER_OF_TIMECARDS];

        Calendar calendar = Calendar.getInstance();
        calendar = new GregorianCalendar(year, month, startDay);
        Date startDate = calendar.getTime();

        // Create some TimeCards
        // The first one
        TimeCard timeCard = new TimeCard(programmer, startDate);
        Date currentDay = startDate;
        timeCard.addConsultantTime(new ConsultantTime(currentDay, clients[FIRST_CLIENT_NDX],
                Skill.SOFTWARE_ENGINEER, STD_WORK_DAY));
        calendar.add(Calendar.DATE, 1);
        currentDay = calendar.getTime();
        timeCard.addConsultantTime(new ConsultantTime(currentDay, clients[FIRST_CLIENT_NDX],
                Skill.SOFTWARE_ENGINEER, STD_WORK_DAY));
        calendar.add(Calendar.DATE, 1);
        currentDay = calendar.getTime();
        timeCard.addConsultantTime(new ConsultantTime(currentDay, clients[SECOND_CLIENT_NDX],
                Skill.SOFTWARE_ENGINEER, STD_WORK_DAY));
        calendar.add(Calendar.DATE, 1);
        currentDay = calendar.getTime();
        timeCard.addConsultantTime(new ConsultantTime(currentDay, clients[SECOND_CLIENT_NDX],
                Skill.SOFTWARE_ENGINEER, STD_WORK_DAY));
        calendar.add(Calendar.DATE, 1);
        currentDay = calendar.getTime();
        timeCard.addConsultantTime(new ConsultantTime(currentDay, clients[SECOND_CLIENT_NDX],
                Skill.SOFTWARE_ENGINEER, STD_WORK_DAY));
        logger.log(Level.FINEST, "First TimeCard created: ",
                timeCard.toString());
        timeCards[cardNdx++] = timeCard;

        // The second one
        calendar.setTime(startDate);
        calendar.add(Calendar.DATE, DAYS_PER_WEEK);
        startDate = calendar.getTime();
        timeCard = new TimeCard(programmer, startDate);
        currentDay = startDate;
        timeCard.addConsultantTime(new ConsultantTime(currentDay, clients[FIRST_CLIENT_NDX],
                Skill.SOFTWARE_ENGINEER, STD_WORK_DAY));
        calendar.add(Calendar.DATE, 1);
        currentDay = calendar.getTime();
        timeCard.addConsultantTime(new ConsultantTime(currentDay, clients[FIRST_CLIENT_NDX],
                Skill.SOFTWARE_ENGINEER, STD_WORK_DAY));
        calendar.add(Calendar.DATE, 1);
        currentDay = calendar.getTime();
        timeCard.addConsultantTime(new ConsultantTime(currentDay, clients[SECOND_CLIENT_NDX],
                Skill.SOFTWARE_ENGINEER, STD_WORK_DAY));
        calendar.add(Calendar.DATE, 1);
        currentDay = calendar.getTime();
        timeCard.addConsultantTime(new ConsultantTime(currentDay, clients[SECOND_CLIENT_NDX],
                Skill.SOFTWARE_ENGINEER, STD_WORK_DAY + OT_HOURS));
        calendar.add(Calendar.DATE, 1);
        currentDay = calendar.getTime();
        timeCard.addConsultantTime(new ConsultantTime(currentDay, NonBillableAccount.VACATION,
                Skill.SOFTWARE_ENGINEER, STD_WORK_DAY));
        logger.log(Level.FINEST, "Second TimeCard created: ",
                timeCard.toString());
        timeCards[cardNdx++] = timeCard;

        // The third one
        calendar.setTime(startDate);
        timeCard = new TimeCard(systemAnalyst, startDate);
        currentDay = startDate;
        timeCard.addConsultantTime(new ConsultantTime(currentDay, clients[SECOND_CLIENT_NDX],
                Skill.SYSTEM_ARCHITECT, STD_WORK_DAY));
        calendar.add(Calendar.DATE, 1);
        currentDay = calendar.getTime();
        timeCard.addConsultantTime(new ConsultantTime(currentDay, NonBillableAccount.SICK_LEAVE,
                Skill.SYSTEM_ARCHITECT, STD_WORK_DAY));
        calendar.add(Calendar.DATE, 1);
        currentDay = calendar.getTime();
        timeCard.addConsultantTime(new ConsultantTime(currentDay, clients[SECOND_CLIENT_NDX],
                Skill.SYSTEM_ARCHITECT, STD_WORK_DAY));
        calendar.add(Calendar.DATE, 1);
        currentDay = calendar.getTime();
        timeCard.addConsultantTime(new ConsultantTime(currentDay, clients[SECOND_CLIENT_NDX],
                Skill.SYSTEM_ARCHITECT, STD_WORK_DAY));
        calendar.add(Calendar.DATE, 1);
        currentDay = calendar.getTime();
        timeCard.addConsultantTime(new ConsultantTime(currentDay, clients[SECOND_CLIENT_NDX],
                Skill.SYSTEM_ARCHITECT, STD_WORK_DAY));
        calendar.add(Calendar.DATE, 1);
        currentDay = calendar.getTime();
        timeCard.addConsultantTime(new ConsultantTime(currentDay, clients[SECOND_CLIENT_NDX],
                Skill.SYSTEM_ARCHITECT, STD_WORK_DAY));
        logger.log(Level.FINEST, "Third TimeCard created: ",
                timeCard.toString());
        timeCards[cardNdx++] = timeCard;

        // The forth one
        calendar.setTime(startDate);
        calendar.add(Calendar.DATE, DAYS_PER_WEEK);
        //calendar.set(year, month, startDay + DAYS_PER_WEEK);
        startDate = calendar.getTime();
        timeCard = new TimeCard(systemAnalyst, startDate);
        currentDay = startDate;
        timeCard.addConsultantTime(new ConsultantTime(currentDay, clients[SECOND_CLIENT_NDX],
                Skill.SYSTEM_ARCHITECT, STD_WORK_DAY));
        calendar.add(Calendar.DATE, 1);
        currentDay = calendar.getTime();
        timeCard.addConsultantTime(new ConsultantTime(currentDay, clients[SECOND_CLIENT_NDX],
                Skill.SYSTEM_ARCHITECT, STD_WORK_DAY));
        calendar.add(Calendar.DATE, 1);
        currentDay = calendar.getTime();
        timeCard.addConsultantTime(new ConsultantTime(currentDay,
                NonBillableAccount.BUSINESS_DEVELOPMENT, Skill.SYSTEM_ARCHITECT, STD_WORK_DAY));
        calendar.add(Calendar.DATE, 1);
        currentDay = calendar.getTime();
        timeCard.addConsultantTime(new ConsultantTime(currentDay,
                NonBillableAccount.BUSINESS_DEVELOPMENT, Skill.SYSTEM_ARCHITECT, STD_WORK_DAY));
        calendar.add(Calendar.DATE, 1);
        currentDay = calendar.getTime();
        timeCard.addConsultantTime(new ConsultantTime(currentDay, clients[SECOND_CLIENT_NDX],
                Skill.SYSTEM_ARCHITECT, STD_WORK_DAY));
        logger.log(Level.FINEST, "Forth TimeCard created: ",
                timeCard.toString());
        timeCards[cardNdx++] = timeCard;

        return timeCards;
    }

    /**
     * Print the time card instances.
     *
     * @param timeCards the time cards to print
     * @param out The output stream; can be System.out or a text file.
     */
    private static void printTimeCards(final TimeCard[] timeCards, final PrintStream out) {
        for (final TimeCard timeCard : timeCards) {
            out.print(timeCard.toString());
        }
    }

    /**
     * The application method.
     *
     * @param args Command line arguments.
     */
    public static void main(final String[] args) {
        final ClientAccount[] clients = createClientAccounts();
        final TimeCard[] cards = createTimeCards(clients,
                TEST_MONTH, TEST_START_FIRST_WEEK, TEST_YEAR);
        // Print 'em
        printTimeCards(cards, System.out);
    }

}
