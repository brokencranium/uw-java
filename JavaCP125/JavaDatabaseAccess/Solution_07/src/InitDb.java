import java.util.ArrayList;
import java.util.List;

import com.scg.domain.ClientAccount;
import com.scg.domain.Consultant;
import com.scg.domain.TimeCard;
import com.scg.persistent.DbServer;

/**
 * The initialize/populate the database.
 *
 * @author Russ Moul
 */
public final class InitDb {
    /** The database URL. */
    private static final String DB_URL = "jdbc:mysql://localhost/scgDB";

    /** The database account name. */
    private static final String DB_ACCOUNT = "student";

    /** The database account password. */
    private static final String DB_PASSWORD = "student";

    /**
     * Prevent instantiation.
     */
    private InitDb() {
    }

    /**
     * Entry point.
     *
     * @param args not used.
     *
     * @throws Exception if anything goes awry
     */
    public static void main(final String[] args) throws Exception {
        final List<ClientAccount> accounts = new ArrayList<ClientAccount>();
        final List<Consultant> consultants = new ArrayList<Consultant>();
        final List<TimeCard> timeCards = new ArrayList<TimeCard>();
        ListFactory.populateLists(accounts, consultants, timeCards);

        final  DbServer db = new DbServer(DB_URL,
                                          DB_ACCOUNT, DB_PASSWORD);

        for (final ClientAccount client : accounts) {
            db.addClient(client);
        }

        for (final Consultant consultant : consultants) {
            db.addConsultant(consultant);
        }

        for (final TimeCard timeCard : timeCards) {
            db.addTimeCard(timeCard);
        }
    }
}
