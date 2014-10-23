package com.scg.persistent;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import com.scg.domain.ClientAccount;
import com.scg.domain.Consultant;
import com.scg.domain.ConsultantTime;
import com.scg.domain.Invoice;
import com.scg.domain.InvoiceLineItem;
import com.scg.domain.Skill;
import com.scg.domain.TimeCard;
import com.scg.util.Address;
import com.scg.util.DateRange;
import com.scg.util.Name;
import com.scg.util.StateCode;

/**
 * Responsible for providing a programmatic interface to store and access objects
 * in the database.
 *
 * @author Russ Moul
 */
public final class DbServer {
    /** SQL for inserting a client. */
    private static final String CLIENT_INSERT_SQL =
            "INSERT INTO clients (name, street, city, state, postal_code, "
          + "contact_last_name, contact_first_name, contact_middle_name) "
          + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

    /** SQL for selecting all clients. */
    private static final String CLIENT_ALL_SELECT_SQL =
            "SELECT name, street, city, state, postal_code, "
          + "contact_last_name, contact_first_name, contact_middle_name "
          + "FROM clients";

    /** SQL for inserting a consultant. */
    private static final String CONSULTANT_INSERT_SQL =
            "INSERT INTO consultants (last_name, first_name, middle_name) "
          + "VALUES (?, ?, ?)";

    /** SQL for selecting all consultants. */
    private static final String CONSULTANT_ALL_SELECT_SQL =
            "SELECT last_name, first_name, middle_name "
          + "FROM consultants";

    /** SQL for inserting a timecard. */
    private static final String TIMECARD_INSERT_SQL =
            "INSERT INTO timecards (consultant_id, start_date) "
          + "VALUES (?, ?)";

    /** SQL for obtaining the consultant id of a consultant. */
    private static final String CONSULTANT_ID_SELECT_SQL =
            "SELECT id "
          +   "FROM consultants "
          +  "WHERE last_name = ? "
          +    "AND first_name = ? "
          +    "AND middle_name = ?";

    /** SQL for obtaining the last identity value generated on a connection. */
    private static final String LAST_IDENTITY_SQL =
            "SELECT LAST_INSERT_ID()";

    /** SQL for inserting a billable hours record. */
    private static final String BILLABLE_HOURS_INSERT_SQL =
            "INSERT INTO billable_hours (client_id, "
          + "timecard_id, date, skill, hours) "
          + "VALUES ((SELECT DISTINCT id "
          +               "FROM clients "
          +              "WHERE name = ?), ? , ?, ?, ?)";

    /** SQL for inserting a non billable hours record. */
    private static final String NON_BILLABLE_HOURS_INSERT_SQL =
            "INSERT INTO non_billable_hours (account_name, "
          + "timecard_id, date, hours) "
          + "VALUES (?, ? , ?, ?)";

    /** SQL for selecting all the invoice items for a client and month. */
    private static final String INVOICE_ITEMS_SELECT_SQL =
            "SELECT b.date, c.last_name, c.first_name, c.middle_name, "
          +        "b.skill, s.rate, b.hours"
          + "  FROM billable_hours b, consultants c, skills s, timecards t"
          + " WHERE b.client_id = (SELECT DISTINCT id"
          +                       "  FROM clients"
          +                       " WHERE name = ?)"
          + "   AND b.skill = s.name"
          + "   AND b.timecard_id = t.id"
          + "   AND c.id = t.consultant_id"
          + "   AND b.date >= ?"
          + "   AND b.date <= ?";


    /** This class' logger. */
    private static final Logger log = Logger.getLogger(DbServer.class.getName());
    /** The database URL. */
    private final String dbUrl;

    /** Username for accessing the database. */
    private final String username;

    /** Password for the username. */
    private final String password;

    /**
     * Constructor.
     *
     * @param dbUrl the database URL
     * @param username the database username
     * @param password the database password
     *
     */
    public DbServer(final String dbUrl,
                    final String username, final String password) {
        this.dbUrl = dbUrl;
        this.username = username;
        this.password = password;
    }

    /**
     * Add a client to the database.
     *
     * @param client the client to add
     *
     * @throws SQLException if any database operations fail
     */
    public void addClient(final ClientAccount client) throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DriverManager.getConnection(dbUrl, username, password);
            ps = conn.prepareStatement(CLIENT_INSERT_SQL);
            ps.setString(1, client.getName());
            final Address address = client.getAddress();
            ps.setString(2, address.getStreetNumber());
            ps.setString(3, address.getCity());
            ps.setString(4, address.getState().toString());
            ps.setString(5, address.getPostalCode());
            final Name contact = client.getContact();
            ps.setString(6, contact.getLastName());
            ps.setString(7, contact.getFirstName());
            ps.setString(8, contact.getMiddleName());

            ps.executeUpdate();
        } finally {
            if (ps != null) {
                ps.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
    }

    /**
     * Get all of the clients in the database.
     *
     * @return a list of all of the clients
     *
     * @throws SQLException if any datbase operations fail
     */
    public List<ClientAccount> getClients() throws SQLException {
        final List<ClientAccount> clients = new ArrayList<ClientAccount>();
        Connection conn = null;
        Statement stmnt = null;
        ResultSet rs = null;
        try {
            conn = DriverManager.getConnection(dbUrl, username, password);
            stmnt = conn.createStatement();
            rs = stmnt.executeQuery(CLIENT_ALL_SELECT_SQL);
            while (rs.next()) {
                final String name = rs.getString(1);
                final String street = rs.getString(2);
                final String city = rs.getString(3);
                final StateCode state = StateCode.valueOf(rs.getString(4));
                final String postalCode = rs.getString(5);
                final String contactLastName = rs.getString(6);
                final String contactFirstName = rs.getString(7);
                final String contactMiddleName = rs.getString(8);
                final ClientAccount client =
                    new ClientAccount(name,
                        new Name(contactLastName, contactFirstName, contactMiddleName),
                        new Address(street, city, state, postalCode));
                clients.add(client);
            }
            return clients;
        } finally {
            if (stmnt != null) {
                stmnt.close();
            }
            if (rs != null) {
                rs.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
    }

    /**
     * Add a consultant to the database.
     *
     * @param consultant the consultant to add
     *
     * @throws SQLException if any database operations fail
     */
    public void addConsultant(final Consultant consultant)
        throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DriverManager.getConnection(dbUrl, username, password);

            ps = conn.prepareStatement(CONSULTANT_INSERT_SQL);
            final Name name = consultant.getName();
            ps.setString(1, name.getLastName());
            ps.setString(2, name.getFirstName());
            ps.setString(3, name.getMiddleName());

            ps.executeUpdate();
        } finally {
            if (ps != null) {
                ps.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
    }

    /**
     * Get all of the consultant in the database.
     *
     * @return a list of all of the consultants
     *
     * @throws SQLException if any datbase operations fail
     */
    public List<Consultant> getConsultants() throws SQLException {
        final List<Consultant> consultants = new ArrayList<Consultant>();
        Connection conn = null;
        Statement stmnt = null;
        ResultSet rs = null;

        try {
            conn = DriverManager.getConnection(dbUrl, username, password);

            stmnt = conn.createStatement();
            rs = stmnt.executeQuery(CONSULTANT_ALL_SELECT_SQL);
            while (rs.next()) {
                final String lastName = rs.getString(1);
                final String firstName = rs.getString(2);
                final String middleName = rs.getString(3);
                final Consultant consultant = new Consultant(
                                              new Name(lastName, firstName, middleName));
                consultants.add(consultant);
            }
            return consultants;
        } finally {
            if (stmnt != null) {
                stmnt.close();
            }
            if (rs != null) {
                rs.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
    }

    /**
     * Add a timecard to the database.
     *
     * @param timeCard the timecard to add
     *
     * @throws SQLException if any database operations fail
     */
    public void addTimeCard(final TimeCard timeCard)
        throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Statement stmnt = null;
        PreparedStatement billableStmnt = null;
        PreparedStatement nonbillableStmnt = null;

        try {
            conn = DriverManager.getConnection(dbUrl, username, password);

            // get the consultant id
            ps = conn.prepareStatement(CONSULTANT_ID_SELECT_SQL);
            final Name name = timeCard.getConsultant().getName();
            ps.setString(1, name.getLastName());
            ps.setString(2, name.getFirstName());
            ps.setString(3, name.getMiddleName());

            rs = ps.executeQuery();
            if (rs.next()) {
                final int consultantId = rs.getInt(1);
                rs.close();
                ps.close();

                // insert the time card record
                ps = conn.prepareStatement(TIMECARD_INSERT_SQL);
                ps.setInt(1, consultantId);
                ps.setDate(2, new Date(timeCard.getWeekStartingDay().getTime()));

                ps.executeUpdate();

                // get the identity of the inserted row
                stmnt = conn.createStatement();
                rs = stmnt.executeQuery(LAST_IDENTITY_SQL);
                if (rs.next()) {
                    final int timeCardId = rs.getInt(1);

                    final List<ConsultantTime> entries = timeCard.getConsultingHours();

                    // prepare the statements
                    billableStmnt = conn.prepareStatement(BILLABLE_HOURS_INSERT_SQL);
                    nonbillableStmnt = conn.prepareStatement(NON_BILLABLE_HOURS_INSERT_SQL);
                    // insert the hours records
                    for (final ConsultantTime entry : entries) {
                        ps = (entry.isBillable()) ? billableStmnt : nonbillableStmnt;
                        int ndx = 1;
                        ps.setString(ndx++, entry.isBillable() ? entry.getAccount().getName()
                                                               : entry.getAccount().toString());
                        ps.setInt(ndx++, timeCardId);
                        ps.setDate(ndx++, new Date(entry.getDate().getTime()));
                        if (entry.isBillable()) {
                            ps.setString(ndx++, entry.getSkill().toString());
                        }
                        ps.setInt(ndx++, entry.getHours());

                        ps.executeUpdate();
                    }
                } else {
                    log.severe("Unable to locate inserted timecard.");
                }
            } else {
                log.severe("Unknown client.");
            }
        } finally {
            if (ps != null) {
                ps.close();
            }
            if (stmnt != null) {
                stmnt.close();
            }
            if (billableStmnt != null) {
                billableStmnt.close();
            }
            if (nonbillableStmnt != null) {
                nonbillableStmnt.close();
            }
            if (rs != null) {
                rs.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
    }

    /**
     * Get clients monthly invoice.
     *
     * @param client the client to obtain the invoice line items for
     * @param month the month of the invoice
     * @param year the year of the invoice
     *
     * @return the clients invoice for the month
     *
     * @throws SQLException if any database operations fail
     */
    public Invoice getInvoice(final ClientAccount client, final int month, final int year)
        throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        final Invoice invoice = new Invoice(client, month, year);

        try {
            conn = DriverManager.getConnection(dbUrl, username, password);
            // determine the start and end of the month
            final DateRange dr = new DateRange(month, year);

            // get the invoice line items
            ps = conn.prepareStatement(INVOICE_ITEMS_SELECT_SQL);
            ps.setString(1, client.getName());
            ps.setDate(2, new Date(dr.getStartDate().getTime()));
            ps.setDate(3, new Date(dr.getEndDate().getTime()));

            rs = ps.executeQuery();
            while (rs.next()) {
                final Date date = rs.getDate(1);
                final String cLastName = rs.getString(2);
                final String cFirstName = rs.getString(3);
                final String cMiddleName = rs.getString(4);
                final String skill = rs.getString(5);
                final int hours = rs.getInt(7);

                final Consultant consultant = new Consultant(
                                              new Name(cLastName, cFirstName,
                                                       cMiddleName));
                final Skill sk = Skill.valueOf(skill);

                final InvoiceLineItem item = new InvoiceLineItem(date,
                                                                 consultant,
                                                                 sk, hours);
                invoice.addLineItem(item);
            }
        } finally {
            if (ps != null) {
                ps.close();
            }
            if (rs != null) {
                rs.close();
            }

            if (conn != null) {
                conn.close();
            }
        }
        return invoice;
    }
}
