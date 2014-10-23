import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.scg.domain.ClientAccount;
import com.scg.domain.Consultant;
import com.scg.domain.TimeCard;
import com.scg.net.client.InvoiceClient;

/**
 * The Client for Solution09
 *
 * @author Russ Moul
 */
public final class Assignment09 {
    /** Localhost. */
    private static final String LOCALHOST = "127.0.0.1";
    /**
     * Prevent instantiation.
     */
    private Assignment09() {
    }

    /**
     * Test the Solution 09 Client.
     *
     * @param args Command line parameters - not used.
     *
     * @throws Exception if any occur
     */
    public static void main(final String[] args) throws Exception {
        final List<ClientAccount> accounts = new ArrayList<ClientAccount>();
        final List<Consultant> consultants = new ArrayList<Consultant>();
        final List<TimeCard> timeCards = new ArrayList<TimeCard>();
        ListFactory.populateLists(accounts, consultants, timeCards);

        final List<TimeCard> immutableTimeCards = Collections.unmodifiableList(timeCards);
        final InvoiceClient netClient01 =
            new InvoiceClient(LOCALHOST, Assignment09Server.DEFAULT_PORT, immutableTimeCards);
        final InvoiceClient netClient02 =
            new InvoiceClient(LOCALHOST, Assignment09Server.DEFAULT_PORT, immutableTimeCards);
        final InvoiceClient netClient03 =
            new InvoiceClient(LOCALHOST, Assignment09Server.DEFAULT_PORT, immutableTimeCards);
        final InvoiceClient netClient04 =
            new InvoiceClient(LOCALHOST, Assignment09Server.DEFAULT_PORT, immutableTimeCards);

        netClient01.start();
        netClient02.start();
        netClient03.start();
        netClient04.start();

        netClient01.join();
        netClient02.join();
        netClient03.join();
        netClient04.join();

        // Sent quit command
        final InvoiceClient shutdownClient = new InvoiceClient(LOCALHOST, Assignment09Server.DEFAULT_PORT, null);
        shutdownClient.sendQuit();
    }

}
