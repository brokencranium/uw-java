package com.scg.net.server;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.scg.domain.ClientAccount;
import com.scg.domain.Consultant;

/**
 * The server for creation of client invoices based on time cards sent from the
 * client.
 *
 * @author Russ Moul
 */
public final class InvoiceServer {
    /** This class' logger. */
    private static final Logger logger =
                         Logger.getLogger(InvoiceServer.class.getName());

    /** The server directory name. */
    private static final String SERVER_DIR_NAME = "server/";

    /** The server socket created for each connection. */
    private final ServerSocket serverSocket;

    /** The list of clients maintained by this server. */
    private final List<ClientAccount> clientList;

    /** The list of consultants maintained by this server. */
    private final List<Consultant> consultantList;

    /** The socket encapsulating a client connection. */
    private Socket client;

    /**
     * The number assigned to each CommndProcessor as it is created and
     * launched.
     */
    private int processorNumber = 1;

    /**
     * Construct an InvoiceServer with a port.
     *
     * @param port The port for this server to listen on
     * @param clientList the initial list of clients
     * @param consultantList the initial list of consultants
     *
     * @throws IOException if an IO error occurs
     */
    public InvoiceServer(final int port,
                         final List<ClientAccount> clientList,
                         final List<Consultant> consultantList)
        throws IOException {
        this.clientList = clientList;
        this.consultantList = consultantList;
        serverSocket = new ServerSocket(port);
        logger.info("InvoiceServer started on: "
                + serverSocket.getInetAddress().getHostName() + ":"
                + serverSocket.getLocalPort());

        Runtime.getRuntime().addShutdownHook(
                        new InvoiceServerShutdownHook(clientList,
                                                      consultantList));
    }

    /**
     * Run this server, establishing connections, receiving commands, and
     * sending them to the CommandProcesser.
     */
    public void run() {
        while (!serverSocket.isClosed()) {
            try {
                logger.info("InvoiceServer waiting for connection.");
                client = serverSocket.accept();
                final CommandProcessor commandProcessor =
                    new CommandProcessor(client,
                                         "command processor " + processorNumber,
                                         clientList, consultantList, this);
                final File serverDir = new File(SERVER_DIR_NAME);
                if (serverDir.exists() || serverDir.mkdir()) {
                    commandProcessor.setOutPutDirectoryName(SERVER_DIR_NAME);
                    final Thread thread = new Thread(commandProcessor,
                                "CommandProcessor_" + processorNumber++);
                    thread.start();
                } else {
                    logger.severe("Unable to create output directory, "
                                + serverDir.getAbsolutePath());
                }
            } catch (final IOException ex) {
                if (!serverSocket.isClosed()) {
                    logger.log(Level.SEVERE, "Connection accept failed.", ex);
                    try {
                        serverSocket.close();
                    } catch (final IOException e) {
                        logger.log(Level.SEVERE, "Unable to close listening socket.", e);
                    }
                } else {
                    logger.log(Level.INFO, "Shuting down due to quit command from client.");
                }
            }
        }
    }

    /**
     * Shutdown the server.
     */
    void shutdown() {
        try {
            if (!serverSocket.isClosed()) {
                serverSocket.close();
            }
        } catch (final IOException e) {
            logger.log(Level.SEVERE, "Shutdown unable to close listening socket.", e);
        }
    }
}
