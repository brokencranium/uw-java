import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.scg.domain.ClientAccount;
import com.scg.domain.Consultant;
import com.scg.domain.Invoice;
import com.scg.domain.TimeCard;

/**
 * Assignment 03 application.
 */
public final class Assignment03 {
    /** This class' logger. */
    private static final Logger log = Logger.getLogger(Assignment03.class.getName());

    /** The invoice month. */
    private static final int INVOICE_MONTH = Calendar.MARCH;

    /** The test year. */
    private static final int INVOICE_YEAR = 2006;

    /**
     * Prevent instantiation.
     */
    private Assignment03() {
    }

    /**
     * Create invoices for the clients from the timecards.
     *
     * @param accounts the accounts to create the invoices for
     * @param timeCards the time cards to create the invoices from
     *
     * @return the created invoices
     */
    private static List<Invoice> createInvoices(final List<ClientAccount> accounts,
                                                final List<TimeCard> timeCards) {
        final List<Invoice> invoices = new ArrayList<Invoice>(accounts.size());
        for (ClientAccount account : accounts) {
            final Invoice invoice = new Invoice(account, INVOICE_MONTH, INVOICE_YEAR);
            invoices.add(invoice);
            for (TimeCard currentTimeCard : timeCards) {
                invoice.extractLineItems(currentTimeCard);
            }
        }

        return invoices;
    }

    /**
     * Print the invoice to a PrintStream.
     *
     * @param invoices the invoices to print
     * @param out The output stream; can be System.out or a text file.
     */
    private static void printInvoices(final List<Invoice> invoices, final PrintStream out) {
        for (Invoice invoice : invoices) {
            out.println(invoice.toString());
        }
    }

    /**
     * The application method.
     *
     * @param args Command line arguments.
     */
    public static void main(final String[] args) {
        // Create lists to be populated by factory
        final List<ClientAccount> accounts = new ArrayList<ClientAccount>();
        final List<Consultant> consultants = new ArrayList<Consultant>();
        final List<TimeCard> timeCards = new ArrayList<TimeCard>();
        ListFactory.populateLists(accounts, consultants, timeCards);
        // Print them
        ListFactory.printTimeCards(timeCards, System.out);

        // Create the Invoices
        final List<Invoice> invoices = createInvoices(accounts, timeCards);
        // Print them
        System.out.println();
        System.out.println("==================================================================================");
        System.out.println("=============================== I N V O I C E S ==================================");
        System.out.println("==================================================================================");
        System.out.println();
        printInvoices(invoices, System.out);
        // Now print it to a file
        PrintStream writer;
        try {
            writer = new PrintStream(new FileOutputStream("invoice.txt"));
            printInvoices(invoices, writer);
        } catch (final IOException ex) {
            log.log(Level.SEVERE, "Unable to print invoice.", ex);
        }
    }
}
