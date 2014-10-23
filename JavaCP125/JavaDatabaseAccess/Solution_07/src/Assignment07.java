import java.util.Calendar;

import com.scg.domain.ClientAccount;
import com.scg.domain.Consultant;
import com.scg.domain.Invoice;
import com.scg.persistent.DbServer;

/**
 * Creates an invoice from the data in the database.
 *
 * @author Russ Moul
 */
public final class Assignment07 {
    /** The start month for our test cases. */
    private static final int INVOICE_MONTH = Calendar.MARCH;

    /** The test year. */
    private static final int INVOICE_YEAR = 2006;

    /** The database URL. */
    private static final String DB_URL = "jdbc:mysql://localhost/scgDB";

    /** The database account name. */
    private static final String DB_ACCOUNT = "student";

    /** The database account password. */
    private static final String DB_PASSWORD = "student";

    /**
     * Prevent instantiation.
     */
    private Assignment07() {
    }

    /**
     * Entry point.
     *
     * @param args not used.
     *
     * @throws Exception if anything goes awry
     */
    public static void main(final String[] args) throws Exception {
        final  DbServer db = new DbServer(DB_URL,
                                          DB_ACCOUNT, DB_PASSWORD);

        // print out the consultants
        System.out.println("The consultants of SCG");
        for (final Consultant consultant : db.getConsultants()) {
            System.out.println(consultant.getName());
        }

        System.out.println();
        System.out.println();

        // generate the march 2006 invoice for each client
        for (final ClientAccount client : db.getClients()) {
            System.out.println(client.getName());

            final Invoice invoice = db.getInvoice(client, INVOICE_MONTH, INVOICE_YEAR);

            System.out.println();
            System.out.println(invoice.toString());
            System.out.println();
        }
    }
}
