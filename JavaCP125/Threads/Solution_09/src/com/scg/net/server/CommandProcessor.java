package com.scg.net.server;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.scg.domain.ClientAccount;
import com.scg.domain.Consultant;
import com.scg.domain.Invoice;
import com.scg.domain.TimeCard;
import com.scg.net.AddClientCommand;
import com.scg.net.AddConsultantCommand;
import com.scg.net.AddTimeCardCommand;
import com.scg.net.Command;
import com.scg.net.CreateInvoicesCommand;
import com.scg.net.DisconnectCommand;
import com.scg.net.ShutdownCommand;
import com.scg.util.DateRange;
import com.scg.util.TimeCardListUtil;

/**
 * The command processor for the invoice server. Implements the receiver role in
 * the Command design pattern.
 *
 * @author Russ Moul
 */
public final class CommandProcessor implements Runnable {
    /** This class' logger. */
    private static final Logger logger =
                         Logger.getLogger(CommandProcessor.class.getName());

    /** The socket connection. */
    private final Socket connection;

    /** The client list to be maintained by this CommandProcessor. */
    private final List<ClientAccount> clientList;

    /** The consultant list to be maintained by this CommandProcessor. */
    private final List<Consultant> consultantList;

    /** The time card list to be maintained by this CommandProcessor. */
    private final List<TimeCard> timeCardList = new ArrayList<TimeCard>();

    /** The name of the directory to be used for files output by commands. */
    private String outputDirectoryName = "server/";

    /** The name assigned to this CommandProcessor, mostly for logging purposes. */
    private final String name;

    /** The server this command processor is spawned from. */
    private final InvoiceServer server;

    /**
     * Construct a CommandProcessor to run in a networked environment.
     *
     * @param connection the Socket connecting the server to the client.
     * @param name the name assigned to this CommandProcessor by the server;
     *             mostly for logging.
     * @param clientList the ClientList to add Clients to.
     * @param consultantList the ConsultantList to add Consultants to.
     * @param server the server that created this command processor
     */
    public CommandProcessor(final Socket connection,
                            final String name,
                            final List<ClientAccount> clientList,
                            final List<Consultant> consultantList,
                            final InvoiceServer server) {
        this.connection = connection;
        this.name = name;
        this.clientList = clientList;
        this.consultantList = consultantList;
        this.server = server;
    }

    /**
     * Set the output directory name.
     *
     * @param outPutDirectoryName the output directory name.
     */
    public void setOutPutDirectoryName(final String outPutDirectoryName) {
        this.outputDirectoryName = outPutDirectoryName;
    }

    /**
     * Execute and AddTimeCardCommand.
     *
     * @param command the command to execute.
     */
    public void execute(final AddTimeCardCommand command) {
        logger.info(String.format("Processor %s executing add time card command: %s",
                                  name, command.getTarget().getConsultant().getName()));
        timeCardList.add(command.getTarget());
    }

    /**
     * Execute an AddClientCommand.
     *
     * @param command the command to execute.
     */
    public void execute(final AddClientCommand command) {
        logger.info(String.format("Processor %s executing add client command: %s",
                                  name, command.getTarget().getName()));
        final ClientAccount newAccount = command.getTarget();
        synchronized (clientList) {
            if (!clientList.contains(newAccount)) {
                clientList.add(newAccount);
            }
        }
    }

    /**
     * Execute and AddConsultantCommand.
     *
     * @param command the command to execute.
     */
    public void execute(final AddConsultantCommand command) {
        logger.info(String.format("Processor %s executing add consultant command: %s",
                                  name, command.getTarget().getName()));
        final Consultant newConsultant = command.getTarget();
        synchronized (consultantList) {
            if (!consultantList.contains(newConsultant)) {
                consultantList.add(newConsultant);
            }
        }
    }

    /**
     * Execute a CreateInvoicesCommand.
     *
     * @param command the command to execute.
     */
    public void execute(final CreateInvoicesCommand command) {
        logger.info(String.format("Processor %s executing invoices command: %s",
                                  name, command));
        Invoice invoice = null;
        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(command.getTarget());
        final SimpleDateFormat formatter = new SimpleDateFormat("MMMMyyyy");
        final String monthString = formatter.format(calendar.getTime());
        synchronized (clientList) {
            for (final ClientAccount client : clientList) {
                invoice = new Invoice(client, calendar.get(Calendar.MONTH),
                                              calendar.get(Calendar.YEAR));
                List<TimeCard> timeCardListForClient;
                timeCardListForClient = TimeCardListUtil.getTimeCardsForDateRange(
                        timeCardList, new DateRange(calendar.get(Calendar.MONTH),
                                                    calendar.get(Calendar.YEAR)));

                for (final TimeCard currentTimeCard : timeCardListForClient) {
                    invoice.extractLineItems(currentTimeCard);
                }

                final String outFileName = String.format("%s%s%sInvoice-%d.txt",
                        outputDirectoryName, client.getName().replaceAll(" ", ""),
                        monthString, connection.getPort());
                PrintStream printOut = null;
                try {
                    printOut = new PrintStream(new FileOutputStream(outFileName), true);
                    printInvoice(invoice, printOut);
                } catch (final FileNotFoundException e) {
                    logger.log(Level.SEVERE, "Can't open file " + outFileName, e);
                } finally {
                    if (printOut != null) {
                        printOut.close();
                    }
                }
            }
        }
    }

    /**
     * Execute a DisconnectCommand.
     *
     * @param command the input DisconnectCommand.
     */
    public void execute(final DisconnectCommand command) {
        logger.info(String.format("Processor %s executing disconnect command: %s",
                                  name, command));
        try {
            connection.close();
        } catch (final IOException e) {
            logger.log(Level.WARNING, "Disconnect unable to close client connection.", e);
        }
    }

    /**
     * Execute a ShutdownCommand.
     *
     * @param command the input ShutdownCommand.
     */
    public void execute(final ShutdownCommand command) {
        logger.info(String.format("Processor %s executing shutdown command: %s",
                                  name, command));
        try {
            connection.close();
        } catch (final IOException e) {
            logger.log(Level.WARNING, "Shutdown unable to close client connection.", e);
        }
        server.shutdown();
    }

    /**
     * Print the invoice to a PrintStream.
     *
     * @param invoice the invoice to print.
     * @param printOut The output stream; can be System.out or a text file.
     */
    private void printInvoice(final Invoice invoice, final PrintStream printOut) {
        printOut.println(invoice.toString());
    }

    /**
     * Run this CommandProcessor.
     */
    @Override
    public void run() {
        logger.info(String.format("Processor %s running.", name));
        logger.info("Connection made.");
        ObjectInputStream in = null;
        try {
            in = new ObjectInputStream(connection.getInputStream());
            connection.shutdownOutput();
        } catch (final IOException ex) {
            logger.log(Level.SEVERE, "Attempt to get input stream from socket.", ex);
            return;
        }
        try {
                        
            while (!connection.isClosed()) {
                final Object obj = in.readObject();
                if (obj instanceof Command<?>) {
                    final Command<?> command = (Command<?>)obj;
                    logger.info(String.format("Received command: %s",
                                command.getClass().getSimpleName()));
                    command.setReceiver(this);
                    command.execute();
                } else {
                    logger.warning(String.format("Received non command object, %s, discarding.",
                                                 obj.getClass().getSimpleName()));
                }
            }
        } catch (final IOException ex) {
            logger.log(Level.SEVERE, "Attempt to read command failed.", ex);
        } catch (final ClassNotFoundException ex) {
            logger.log(Level.SEVERE, "Read command of an unknown type.", ex);

        } finally {
            try {
                in.close();
                connection.close();
            } catch (final IOException ex) {
                logger.log(Level.SEVERE, "Attempt to close connection failed.", ex);
            }
        }
    }

}
