package com.scg.domain;

import java.util.Date;
import java.util.Locale;

/**
 * Encapsulates a single billable item to be included in an invoice.
 *
 * @author Russ Moul
 */
public final class InvoiceLineItem {
    /** Format string for line item. */
    private static final String LINE_FORMAT = "%1$tm/%1$td/%1$tY  %2$-28s %3$-20s %4$5d    %5$,8.2f";

    /** The date of this line item. */
    private Date date;

    /** The consultant delivering this line item. */
    private Consultant consultant;

    /** The skill delivered for this line item. */
    private Skill skill;

    /** The hours for this line item. */
    private int hours;

    /** The charge for this line item (hours * the skill rate). */
    private int charge;

    /**
     * Construct an InvoiceLineItem.
     *
     * @param date The date of this line item.
     * @param consultant Consultant for this line item.
     * @param skill Skill for this line item.
     * @param hours Hours for this line item.
     */
    public InvoiceLineItem(final Date date, final Consultant consultant,
                           final Skill skill, final int hours) {
        if (hours <= 0) {
            throw new IllegalArgumentException(
                    "InvoiceLineItem requires hours > 0");
        }
        this.date = new Date(date.getTime());
        this.consultant = consultant;
        this.skill = skill;
        this.hours = hours;
        this.charge = skill.getRate() * hours;
    }

    /**
     * Get the consultant for this line item.
     *
     * @return The consultant.
     */
    public Consultant getConsultant() {
        return consultant;
    }

    /**
     * Get the skill for this line item.
     *
     * @return The skill.
     */
    public Skill getSkill() {
        return skill;
    }

    /**
     * Get the hours for this line item.
     *
     * @return The hours.
     */
    public int getHours() {
        return hours;
    }

    /**
     * Get the charge for this line item.
     *
     * @return The charge.
     */
    public int getCharge() {
        return charge;
    }

    /**
     * Print the date, consultant, skill, hours and charge for this line item.
     *
     * @return Formatted string.
     */
    @Override
    public String toString() {
        return String.format(Locale.US, LINE_FORMAT, date, consultant.getName(),
                             skill.getName(), hours, (double)charge);
    }

}
