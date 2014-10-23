package com.scg.net.client;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.scg.domain.ClientAccount;
import com.scg.domain.Consultant;
import com.scg.domain.TimeCard;
import com.scg.net.AddClientCommand;
import com.scg.net.AddConsultantCommand;
import com.scg.net.AddTimeCardCommand;
import com.scg.net.Command;
import com.scg.net.CreateInvoicesCommand;
import com.scg.net.DisconnectCommand;
import com.scg.net.ShutdownCommand;
import com.scg.util.Address;
import com.scg.util.Name;
import com.scg.util.StateCode;

/**
 * The client of the InvoiceServer.
 *
 * @author Russ Moul
 */
public final class InvoiceClient {
    /** This class' logger. */
    private static final Logger logger =
                         Logger.getLogger(InvoiceClient.class.getName());

    /** The invoice month. */
    private static final int INVOICE_MONTH = Calendar.MARCH;

    /** The invoice year. */
    private static final int INVOICE_YEAR = 2006;

   /** The host of the server. */
    private final String host;

    /** The port of the server. */
    private final int port;

    /** The list of time cards. */
    private final List<TimeCard> timeCardList;

    /**
     * Construct an InvoiceClient with a host and port for the server.
     *
     * @param host the host for the server.
     * @param port the port for the server.
     * @param timeCardList the list of timeCards to send to the server
     */
    public InvoiceClient(final String host, final int port, final List<TimeCard> timeCardList) {
        this.host = host;
        this.port = port;
        this.timeCardList = timeCardList;
    }

    /**
     * Runs this InvoiceClient, sending clients, consultants, and time cards to
     * the server, then sending the command to create invoices for a specified
     * month.
     */
    public void run() {
        Socket server = null;
        ObjectOutputStream out = null;
        try {
            server = new Socket(host, port);
            System.out.println("Connected to server at: "
                    + server.getInetAddress().getHostName()
                    + server.getInetAddress().getHostAddress());
            // We don't expect to get any input so shut it down.
            server.shutdownInput();
            out = new ObjectOutputStream(server.getOutputStream());
            sendClients(out);
            sendConsultants(out);
            // make sure we can handle unknown commands
            out.writeObject("NOT_A_COMMAND");
            sendTimeCards(out);
            createInvoices(out, INVOICE_MONTH, INVOICE_YEAR);
            sendDisconnect(out);
            server.shutdownOutput();
        } catch (final IOException ex) {
            logger.log(Level.SEVERE, "Unable to connect to server.", ex);
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (final IOException ex) {
                logger.log(Level.SEVERE, "After shutdown unable to close socket output stream.", ex);
            }
            try {
                if (server != null) {
                    server.close();
                }
            } catch (final IOException ex) {
                logger.log(Level.SEVERE, "After shutdown unable to close connection to server.", ex);
            }
        }
    }

    /**
     * Send the clients to the server.
     *
     * @param out the output stream connecting this client to the server.
     */
    public void sendClients(final ObjectOutputStream out) {
        AddClientCommand command = null;

        // Send new accounts
        ClientAccount client = new ClientAccount("Gotbucks Technologies",
                               new Name("Gotbucks", "Horatio", "$"),
                               new Address("1040 Yellow Brick Road", "Golden", StateCode.CO, "91234"));
        command = new AddClientCommand(client);
        sendCommand(out, command);

        client = new ClientAccount("Nanosoft",
                 new Name("Bridges", "Betty", "S."),
                 new Address("1 Fiber Lane", "Brightville", StateCode.WA, "98234"));
        command = new AddClientCommand(client);
        sendCommand(out, command);
    }

    /**
     * Send the consultants to the server.
     *
     * @param out the output stream connecting this client to the server.
     */
    public void sendConsultants(final ObjectOutputStream out) {
        AddConsultantCommand command = null;

        // Send new consultants
        command = new AddConsultantCommand(new Consultant(
                  new Name("Jones", "FooBar", "Q.")));
        sendCommand(out, command);

        command = new AddConsultantCommand(new Consultant(
                new Name("Bug", "Don", "D.")));
        sendCommand(out, command);
    }

    /**
     * Send the time cards to the server.
     *
     * @param out the output stream connecting this client to the server.
     */
    public void sendTimeCards(final ObjectOutputStream out) {
        AddTimeCardCommand command = null;
        for (final TimeCard timeCard : timeCardList) {
            command = new AddTimeCardCommand(timeCard);
            sendCommand(out, command);
        }
    }

    /**
     * Send the disconnect command to the server.
     *
     * @param out the output stream connecting this client to the server.
     */
    public void sendDisconnect(final ObjectOutputStream out) {
        final DisconnectCommand command = new DisconnectCommand();
        sendCommand(out, command);
    }

    /**
     * Send the command to the server to create invoices for the specified
     * month.
     *
     * @param out the output stream connecting this client to the server.
     * @param month the month to create invoice for
     * @param year the year to create invoice for
     */
    public void createInvoices(final ObjectOutputStream out, final int month, final int year) {
        final Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.YEAR, year);
        final CreateInvoicesCommand command = new CreateInvoicesCommand(calendar.getTime());
        sendCommand(out, command);
    }

    /**
     * Send the quit command to the server.
     */
    public void sendQuit() {
        ObjectOutputStream out = null;
        Socket server = null;
        try {
            server = new Socket(host, port);
            System.err.println("Quit, connected to server at: "
                    + server.getInetAddress().getHostName() + " "
                    + server.getInetAddress().getHostAddress());
            out = new ObjectOutputStream(server.getOutputStream());
            server.shutdownInput();
            final ShutdownCommand command = new ShutdownCommand();
            sendCommand(out, command);
            server.shutdownOutput();
        } catch (final IOException ex) {
            logger.log(Level.SEVERE, "Connect and send quit command.", ex);
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (final IOException ex) {
                logger.log(Level.SEVERE, "Quit unable to close socket output stream.", ex);
            }
            try {
                if (server != null) {
                    server.close();
                }
            } catch (final IOException ex) {
                logger.log(Level.SEVERE, "Quit unable to close connection to server.", ex);
            }
        }
    }

    /**
     * Send a command to the server.
     *
     * @param out the output stream connecting this client to the server
     * @param command the command to send
     */
    private void sendCommand(final ObjectOutputStream out, final Command<?> command) {
        try {
            out.writeObject(command);
            out.flush();
        } catch (final IOException ex) {
            logger.log(Level.SEVERE, "Unable to write command.", ex);
        }
    }
}
