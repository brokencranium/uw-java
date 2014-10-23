package com.scg.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Formatter;
import java.util.List;
import java.util.Locale;

/**
 * Encapsulates a time card capable of storing a consultant's billable and
 * non-billable hours for a week.
 *
 * @author Russ Moul
 */
@SuppressWarnings("serial")
public final class TimeCard implements Comparable<TimeCard>, Serializable {
    /** Factor used in calculating hashCode. */
    private static final int HASH_FACTOR = 37;

    /** Format string for the time card header. */
    private static final String TO_STRING_FORMAT = "TimeCard for: %s, Week Starting: %2$tb %2$td,%2$tY\n";

    /** Format string for the time card header. */
    private static final String HEADER_FORMAT = "Consultant: %-29s Week Starting: %2$tb %2$td,%2$tY\n";

    /** Format string for a line header on the time card. */
    private static final String LINE_HEADER_FORMAT = String.format("%-28s %-10s  %5s  %s%n"
            + "---------------------------  ----------  -----  --------------------%n",
            "Account", "Date", "Hours", "Skill");

    /** A border for the time card */
    private static final String CARD_BORDER = "====================================================================%n";

    /** Format string for a line on the time card. */
    private static final String LINE_FORMAT = "%-28s %2$tm/%2$td/%2$tY  %3$5d  %4$s%n";

    /** Format string for a summary line on the time card. */
    private static final String SUMMARY_LINE_FORMAT = "%-39s  %5d%n";

    /** Format string for the billable time section header on the time card. */
    private static final String BILLABLE_TIME_HEADER_FORMAT = "%nBillable Time:%n";

    /** Format string for the non-billable time section header on the time card. */
    private static final String NON_BILLABLE_TIME_HEADER_FORMAT = "%nNon-billable Time:%n";

    /** Format string for the summary section header on the time card. */
    private static final String SUMMARY_HEADER_FORMAT = "%nSummary:%n";

    /** Holds value of property weekStartingDay. */
    private Date weekStartingDay;

    /** Holds value of property consultant. */
    private Consultant consultant;

    /** Holds value of property totalBillableHours. */
    private int totalBillableHours;

    /** Holds value of property totalNonBillableHours. */
    private int totalNonBillableHours;

    /** Holds value of property consultingHours. */
    private List<ConsultantTime> consultingHours;

    /** Holds value of property totalHours. */
    private int totalHours;


    /**
     * Creates a new instance of TimeCard
     *
     * @param consultant The Consultant whose information this TimeCard records.
     * @param weekStartingDay The date of the first work day of the week this
     *                        TimeCard records information for; assumed to be
     *                        a Monday.
     */
    public TimeCard(final Consultant consultant, final Date weekStartingDay) {
        this.consultant = consultant;
        this.weekStartingDay = new Date(weekStartingDay.getTime());
        this.totalHours = 0;
        this.totalBillableHours = 0;
        this.totalNonBillableHours = 0;
        this.consultingHours = new ArrayList<ConsultantTime>();
    }

    /**
     * Getter for property consultant.
     *
     * @return Value of property consultant.
     */
    public Consultant getConsultant() {
        return this.consultant;
    }

    /**
     * Getter for property billableHours.
     *
     * @return Value of property billableHours.
     */
    public int getTotalBillableHours() {
        return this.totalBillableHours;
    }

    /**
     * Getter for property totalNonBillableHours.
     *
     * @return Value of property totalNonBillableHours.
     */
    public int getTotalNonBillableHours() {
        return this.totalNonBillableHours;
    }

    /**
     * Getter for property consultingHours.
     *
     * @return Value of property consultingHours.
     */
    public List<ConsultantTime> getConsultingHours() {
        return Collections.unmodifiableList(this.consultingHours);
    }

    /**
     * Add a ConsultantTime object to this TimeCard.
     *
     * @param consultantTime The ConsultantTime to add.
     */
    public void addConsultantTime(final ConsultantTime consultantTime) {
        consultingHours.add(consultantTime);
        final int addedHours = consultantTime.getHours();
        if (consultantTime.isBillable()) {
            totalBillableHours += addedHours;
        } else {
            totalNonBillableHours += addedHours;
        }
        totalHours += addedHours;
    }

    /**
     * Getter for property totalHours.
     *
     * @return Value of property totalHours.
     */
    public int getTotalHours() {
        return this.totalHours;
    }

    /**
     * Getter for property weekStartingDay.
     *
     * @return Value of property weekStartingDay.
     */
    public Date getWeekStartingDay() {
        return new Date(weekStartingDay.getTime());
    }

    /**
     * Returns the billable hours (if any) in this TimeCard for the specified
     * Client.
     *
     * @param clientName
     *            name of the client to extract hours for.
     * @return list of billable hours for the client.
     */
    public List<ConsultantTime> getBillableHoursForClient(final String clientName) {
        final ArrayList<ConsultantTime> billableConsultingHours = new ArrayList<ConsultantTime>();
        for (ConsultantTime currentTime : consultingHours) {
            if (clientName.equalsIgnoreCase(currentTime.getAccount().getName())) {
                if (currentTime.isBillable()) {
                    billableConsultingHours.add(currentTime);
                }
            }
        }

        return billableConsultingHours;
    }

    /**
     * Add the consulting hours lines to the invoice.
     *
     * @param formatter the formatter to add the lines to
     * @param hours the list of consulting hours
     * @param billable if true billable hours will be added otherwise non-billable
     */
    private void appendTime(final Formatter formatter, final List<ConsultantTime> hours,
                            final boolean billable) {
        for (ConsultantTime currentTime : hours) {
            if (currentTime.isBillable() == billable) {
                formatter.format(LINE_FORMAT, currentTime.getAccount().getName(),
                                              currentTime.getDate(),
                                              currentTime.getHours(),
                                              currentTime.getSkill().getName());
            }
        }
    }

    /**
     * Create a string representation of this object, suitable for printing.
     *
     * @return this TimeCard as a formatted String.
     */
    @Override
    public String toString() {
        return String.format(TO_STRING_FORMAT, consultant.getName(), weekStartingDay);
    }

    /**
     * Create a string representation of this object, suitable for printing the
     * entire timecard.
     *
     * @return this TimeCard as a formatted String.
     */
    public String toReportString() {
        final StringBuilder sb = new StringBuilder();
        final Formatter formatter = new Formatter(sb, Locale.US);
        // Put on a header.
        formatter.format(CARD_BORDER)
                 .format(HEADER_FORMAT, consultant.getName(), weekStartingDay)
                 .format(BILLABLE_TIME_HEADER_FORMAT)
                 .format(LINE_HEADER_FORMAT);

        appendTime(formatter, consultingHours, true);

        formatter.format(NON_BILLABLE_TIME_HEADER_FORMAT)
                 .format(LINE_HEADER_FORMAT);

        appendTime(formatter, consultingHours, false);

        formatter.format(SUMMARY_HEADER_FORMAT)
                 .format(SUMMARY_LINE_FORMAT, "Total Billable:", totalBillableHours)
                 .format(SUMMARY_LINE_FORMAT, "Total Non-Billable:", totalNonBillableHours)
                 .format(SUMMARY_LINE_FORMAT, "Total Hours:", totalHours)
                 .format(CARD_BORDER);

        return formatter.toString();
    }

    /**
     * Compares TimeCard, by ascending date order and consultant, no other
     * elements of the class are considered.
     *
     * @param other the TimeCard to compare to
     *
     * @return a negative integer, zero, or a positive integer as this object is
     *         less than, equal to, or greater than the specified object.
     */
    @Override
    public int compareTo(final TimeCard other) {
        int returnValue;
        
        returnValue = weekStartingDay.compareTo(other.getWeekStartingDay());

        if (returnValue == 0) {
            returnValue = consultant.compareTo(other.getConsultant());
        }
        return returnValue;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        int result = 1;
        result = HASH_FACTOR * result + ((consultant == null) ? 0 : consultant.hashCode());
        result = HASH_FACTOR * result + ((consultingHours == null) ? 0 : consultingHours.hashCode());
        result = HASH_FACTOR * result + totalBillableHours;
        result = HASH_FACTOR * result + totalHours;
        result = HASH_FACTOR * result + totalNonBillableHours;
        result = HASH_FACTOR * result + ((weekStartingDay == null) ? 0 : weekStartingDay.hashCode());
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final TimeCard other = (TimeCard) obj;
        if (consultant == null) {
            if (other.consultant != null) {
                return false;
            }
        } else if (!consultant.equals(other.consultant)) {
            return false;
        }
        if (consultingHours == null) {
            if (other.consultingHours != null) {
                return false;
            }
        } else if (!consultingHours.equals(other.consultingHours)) {
            return false;
        }
        if (totalBillableHours != other.totalBillableHours) {
            return false;
        }
        if (totalHours != other.totalHours) {
            return false;
        }
        if (totalNonBillableHours != other.totalNonBillableHours) {
            return false;
        }
        if (weekStartingDay == null) {
            if (other.weekStartingDay != null) {
                return false;
            }
        } else if (!weekStartingDay.equals(other.weekStartingDay)) {
            return false;
        }
        return true;
    }
}
