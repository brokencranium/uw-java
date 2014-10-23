package com.scg.domain;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Formatter;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.scg.util.Address;
import com.scg.util.DateRange;
import com.scg.util.StateCode;

/**
 * Invoice encapsulates the attributes and behavior to create client invoices
 * for a given time period from time cards.  The invoicing business' name and
 * address are obtained from a properties file. The name of the property file
 * is specified by the PROP_FILE_NAME static member.
 *
 * @author Russ Moul
 */
public final class Invoice {
    /** Name of property file containing invoicing business info. */
    public static final String PROP_FILE_NAME = "invoice.properties";

    /** Property containing the invoicing business name. */
    public static final String BUSINESS_NAME_PROP = "business.name";

    /** Property containing the invoicing business street address. */
    public static final String BUSINESS_STREET_PROP = "business.street";

    /** Property containing the invoicing business city. */
    public static final String BUSINESS_CITY_PROP = "business.city";

    /** Property containing the invoicing business state. */
    public static final String BUSINESS_STATE_PROP = "business.state";

    /** Property containing the invoicing business zip or postal code. */
    public static final String BUSINESS_ZIP_PROP = "business.zip";

    /** String constant for "N/A". */
    public static final String NA = "N/A";

    /** This class' logger. */
    private static final Logger log = Logger.getLogger(Invoice.class.getName());

    /** Items per page. */
    private static final int ITEMS_PER_PAGE = 5;

    /** Client for this Invoice. */
    private final ClientAccount client;

    /** The range of dates this invoice applies to. */
    private final DateRange dateRange;

    /** Date of this Invoice. */
    private final Date invoiceDate;

    /** Total hours for this Invoice. */
    private int totalHours;

    /** Total charges for this Invoice. */
    private int totalCharges;

    /** Container for line items. */
    private final List<InvoiceLineItem> lineItems;

    /** This business' name. */
    private final String bizName;

    /** This business' address. */
    private final Address bizAddress;

    /**
     * Construct an Invoice for a client. The time period is set from the
     * beginning to the end of the month prior to the current month.
     *
     * @param client Client for this Invoice.
     * @param invoiceMonth Month for which this Invoice is being created. This
     *                     parameter is the 0-based month number. The safest
     *                     way for callers to pass a valid value is to use
     *                     Calendar.MONTHNAME for the constant.
     * @param invoiceYear Year for which this Invoice is being created.
     */
    public Invoice(final ClientAccount client, final int invoiceMonth,
                   final int invoiceYear) {
        this.client = client;
        this.lineItems = new ArrayList<InvoiceLineItem>();

        // Create a calendar with today's date.
        final Calendar calendar = Calendar.getInstance();
        this.invoiceDate = calendar.getTime();

        // Set the date range for the invoice
        dateRange = new DateRange(invoiceMonth, invoiceYear);

        final Properties invoiceProps = new Properties();
        final File propFile = new File(PROP_FILE_NAME);
        FileInputStream in = null;
        try {
            in = new FileInputStream(propFile);
            invoiceProps.load(in);
        } catch (final FileNotFoundException e) {
            log.log(Level.WARNING, "Unable to locate properties file, " + propFile.getAbsolutePath(), e);
        } catch (final IOException e) {
            log.log(Level.WARNING, "Unable to read properties file.", e);
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (final IOException e) {
                log.log(Level.WARNING, "Attempt to close properties file failed.", e);
            }
        }
        bizName = invoiceProps.getProperty(BUSINESS_NAME_PROP, NA);
        final String bizStreet = invoiceProps.getProperty(BUSINESS_STREET_PROP, NA);
        final String bizCity = invoiceProps.getProperty(BUSINESS_CITY_PROP, NA);
        final String bizState = invoiceProps.getProperty(BUSINESS_STATE_PROP, "WA");
        final String bizZip = invoiceProps.getProperty(BUSINESS_ZIP_PROP, NA);
        bizAddress = new Address(
                bizStreet, bizCity, StateCode.valueOf(bizState), bizZip);
    }

    /**
     * Get the start date for this Invoice.
     *
     * @return Start date.
     */
    public Date getStartDate() {
        return dateRange.getStartDate();
    }

    /**
     * Get the end date for this Invoice.
     *
     * @return End date.
     */
    public Date getEndDate() {
        return dateRange.getEndDate();
    }

    /**
     * Get the invoice month. This is the 0-based month number.
     *
     * @return the invoice month number.
     */
    public int getInvoiceMonth() {
        final Calendar cal = Calendar.getInstance();
        cal.setTime(dateRange.getStartDate());
        return cal.get(Calendar.MONTH);
    }

    /**
     * Get the total hours for this Invoice.
     *
     * @return Total hours.
     */
    public int getTotalHours() {
        return totalHours;
    }

    /**
     * Get the total charges for this Invoice.
     *
     * @return Total charges.
     */
    public int getTotalCharges() {
        return totalCharges;
    }

    /**
     * Add an invoice line item to this Invoice.
     *
     * @param lineItem InvoiceLineItem to add.
     */
    public void addLineItem(final InvoiceLineItem lineItem) {
        lineItems.add(lineItem);
        totalHours += lineItem.getHours();
        totalCharges += lineItem.getCharge();
    }

    /**
     * Extract the billable hours for this Invoice's client from the input
     * TimeCard and add them to the line items.  Only those hours for the client
     * and month unique to this invoice will be added.
     *
     * @param timeCard the TimeCard potentially containing line items for this
     *                 Invoices client.
     */
    public void extractLineItems(final TimeCard timeCard) {
        final List<ConsultantTime> billableHoursList = timeCard.getBillableHoursForClient(client.getName());
        for (final ConsultantTime consultantTime : billableHoursList) {
            final Date currentDate = consultantTime.getDate();
            if (dateRange.isInRange(currentDate)) {
                final InvoiceLineItem currentItem = new InvoiceLineItem(consultantTime.getDate(),
                                                                        timeCard.getConsultant(),
                                                                        consultantTime.getSkill(),
                                                                        consultantTime.getHours());
                addLineItem(currentItem);
            }
        }
    }

    /**
     * Create a formatted string containing the printable invoice. Prints a
     * header and footer on each page.
     *
     * @return The formatted invoice as a string.
     */
    @Override
    public String toString() {
        final InvoiceHeader invoiceHeader = new InvoiceHeader(bizName, bizAddress, client,
                                                              invoiceDate, dateRange.getStartDate());
        final InvoiceFooter invoiceFooter = new InvoiceFooter(bizName);

        final StringBuffer sb = new StringBuffer();
        final Formatter formatter = new Formatter(sb, Locale.US);

        formatter.format("%s", invoiceHeader);

        for (int i = 0, itemsPrinted = 1; i < lineItems.size(); i++, itemsPrinted++) {
            final InvoiceLineItem invoiceLineItem = lineItems.get(i);
            formatter.format("%s%n", invoiceLineItem);

            if (itemsPrinted % ITEMS_PER_PAGE == 0) {
                invoiceFooter.incrementPageNumber();
                formatter.format("%s%n%s", invoiceFooter, invoiceHeader);
            }
        }
        invoiceFooter.incrementPageNumber();

        formatter.format("%nTotal: %60d  %,10.2f", totalHours, (double)totalCharges)
                 .format("%s", invoiceFooter);

        return formatter.toString();
    }
}
