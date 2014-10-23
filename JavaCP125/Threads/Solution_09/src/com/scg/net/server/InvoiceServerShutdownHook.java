package com.scg.net.server;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.scg.domain.ClientAccount;
import com.scg.domain.Consultant;

/**
 * ShutdownHook for the InvoiceServer.
 *
 * @author Russ Moul
 */
public final class InvoiceServerShutdownHook extends Thread {
    /** How long to wait before shutting down. */
    private static final int SHUTDOWN_DELAY_SECONDS = 5;

    /** One second (1000 milliseconds. */
    private static final int ONE_SECOND = 1000;

    /** This class' logger. */
    private static final Logger logger =
                         Logger.getLogger(InvoiceServerShutdownHook.class.getName());

    /** ClientList to save. */
    private final List<ClientAccount> clientList;

    /** ConsultantList to save. */
    private final List<Consultant> consultantList;

    /** The file where the client list is saved. */
    private final String clientFile = "server/ClientList.txt";

    /** The file where the consultant list is saved. */
    private final String consultantFile = "server/ConsultantList.txt";

    /**
     * Construct an InvoiceServerShutDownHook.
     *
     * @param clientList the ClientList to serialize.
     * @param consultantList the ConsultantList to serialize.
     */
    public InvoiceServerShutdownHook(final List<ClientAccount> clientList,
                                     final List<Consultant> consultantList) {
        this.clientList = clientList;
        this.consultantList = consultantList;
    }

    /**
     * Called by the Runtime when a shutdown signal is received. This will
     * write the client and consultant lists to file, then shut down after
     * SHUTDOWN_DELAY_SECONDS seconds.
     */
    @Override
    public void run() {
        PrintStream clientOut = null;
        PrintStream consultantOut = null;
        try {
            clientOut = new PrintStream(new FileOutputStream(clientFile));
            consultantOut = new PrintStream(new FileOutputStream(consultantFile));
            System.err.println("Saving lists.");
            synchronized (clientList) {
                for (final ClientAccount client : clientList) {
                    clientOut.println(client);
                }
            }
            synchronized (consultantList) {
                for (final Consultant consultant : consultantList) {
                    consultantOut.println(consultant);
                }
            }
        } catch (final IOException ex) {
            logger.log(Level.SEVERE, "Attempt to write lists failed.", ex);
        } finally {
            if (clientOut != null) {
                clientOut.close();
            }
            if (consultantOut != null) {
                consultantOut.close();
            }
        }
        System.err.println("Shutting down invoice server.");
        System.err.println("Starting Shutdown Sequence.");
        System.err.println(String.format("Shutting Down after %d seconds.", SHUTDOWN_DELAY_SECONDS));

        for (int i = SHUTDOWN_DELAY_SECONDS; i > 0; i--) {
            try {
                Thread.sleep(ONE_SECOND);
                System.err.println(String.format("Shutdown in %d seconds", i));
            } catch (final InterruptedException e) {
                // ignore this
                logger.info("Shutdown delay interrupted.");
            }
        }
        System.err.println("Shutdown.");
    }
}
