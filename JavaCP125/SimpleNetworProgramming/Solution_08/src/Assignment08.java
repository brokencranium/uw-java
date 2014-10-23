
import java.util.ArrayList;
import java.util.List;

import com.scg.domain.ClientAccount;
import com.scg.domain.Consultant;
import com.scg.domain.TimeCard;
import com.scg.net.client.InvoiceClient;

/**
 * The client application for Assignment08.
 *
 * @author Russ Moul
 */
public final class Assignment08 {
    /** Localhost. */
    private static final String LOCALHOST = "127.0.0.1";
    /**
     * Prevent instantiation.
     */
    private Assignment08() {
    }

    /**
     * Instantiates an InvoiceClient, provides it with a set of timecards to
     * server the server and starts it running.
     *
     * @param args Command line parameters, not used
     */
    public static void main(final String[] args) throws Exception {
        final List<ClientAccount> accounts = new ArrayList<ClientAccount>();
        final List<Consultant> consultants = new ArrayList<Consultant>();
        final List<TimeCard> timeCards = new ArrayList<TimeCard>();
        ListFactory.populateLists(accounts, consultants, timeCards);

        final InvoiceClient netClient = new InvoiceClient(LOCALHOST,
                                                    Assignment08Server.DEFAULT_PORT, timeCards);
        netClient.run();

        // Sent quit command
        final InvoiceClient shutdownClient = new InvoiceClient(LOCALHOST,
                                                         Assignment08Server.DEFAULT_PORT, null);
        shutdownClient.sendQuit();

        Thread.sleep(2000);
    }

}
