package com.scg.net.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.scg.domain.ClientAccount;
import com.scg.domain.Consultant;
import com.scg.net.Command;

/**
 * The server for creation of account invoices based on time cards sent from the
 * client.
 *
 * @author Russ Moul
 */
public final class InvoiceServer {
    /** The class' logger. */
    private static final Logger logger =
                         Logger.getLogger(InvoiceServer.class.getName());

    /** The clients/accounts. */
    private final List<ClientAccount> clientList;

    /** The consultants. */
    private final List<Consultant> consultantList;

    /** The server socket created for each connection. */
    private final ServerSocket serverSocket;

    /** The socket encapsulating a client connection. */
    private Socket client;

    /**
     * Construct an InvoiceServer with a port.
     *
     * @param port The port for this server to listen on
     * @param clientList the initial list of clients
     * @param consultantList the initial list of consultants
     *
     * @throws IOException in the event of any IO errors
     */
    public InvoiceServer(final int port, final List<ClientAccount> clientList, final List<Consultant> consultantList)
        throws IOException {
        this.clientList = clientList;
        this.consultantList = consultantList;
        serverSocket = new ServerSocket(port);
        logger.info("InvoiceServer started on: "
                  + serverSocket.getInetAddress().getHostName() + ":"
                  + serverSocket.getLocalPort());
    }

    /**
     * Run this server, establishing connections, receiving commands, and
     * sending them to the CommandProcesser.
     */
    public void run() {

        try {
            ObjectInputStream in = null;
            while (!serverSocket.isClosed()) {
                try {
                    logger.info("InvoiceServer waiting for connection.");
                    client = serverSocket.accept();
                    client.shutdownOutput();
                    in = new ObjectInputStream(client.getInputStream());
                    logger.info("Connection made.");
                    final CommandProcessor cmdProc =
                        new CommandProcessor(client, clientList, consultantList, this);
                    while (!client.isClosed()) {
                        final Object obj = in.readObject();
                        if (obj instanceof Command<?>) {
                            final Command<?> command = (Command<?>)obj;
                            logger.info("Received command: "
                                      + command.getClass().getSimpleName());
                            command.setReceiver(cmdProc);
                            command.execute();
                        } else {
                            logger.warning("Received non command object, "
                                         + obj.getClass().getSimpleName()
                                         + ", discarding.");
                        }
                    }
                } catch (final SocketException sx) {
                    logger.info("Server socket closed.");
                } catch (final IOException ex) {
                    logger.log(Level.SEVERE, "IO failure.", ex);
                } catch (final ClassNotFoundException ex) {
                    logger.log(Level.SEVERE, "Unable to resolve read object.", ex);
                } finally {
                    try {
                        if (in != null) {
                            in.close();
                        }
                        if (client != null) {
                            client.close();
                        }
                    } catch (final IOException e) {
                        logger.log(Level.SEVERE, "Unable to close client socket.", e);
                    }
                }
            }
        } finally {
            if (!serverSocket.isClosed()) {
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
