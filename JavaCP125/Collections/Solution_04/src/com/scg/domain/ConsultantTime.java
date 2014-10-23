package com.scg.domain;

import java.util.Date;

/**
 * A consultants time, maintains date, skill, account and hours data.
 *
 * @author Russ Moul
 */
public final class ConsultantTime {

    /** Holds value of property date. */
    private Date date;

    /** Holds value of property account. */
    private Account account;

    /** Holds value of property hours. */
    private int hours;

    /** Holds value of property skill. */
    private final Skill skill;

    /**
     * Creates a new instance of ConsultantTime.
     *
     * @param date The date this instance occurred.
     * @param account The account to charge the hours to; either a Client or
     *                NonBillableAccount.
     * @param skillType The skill type.
     * @param hours The number of hours, which must be positive.
     *
     * @throws IllegalArgumentException if the hours are <=0.
     */
    public ConsultantTime(final Date date, final Account account, final Skill skillType, final int hours) {
        setHours(hours);
        this.date = new Date(date.getTime());
        this.account = account;
        this.skill = skillType;
    }

    /**
     * Getter for property date.
     *
     * @return Value of property date
     */
    public Date getDate() {
        return (date == null) ? (Date)null : new Date(date.getTime());
    }

    /**
     * Setter for property date.
     *
     * @param date New value of property date
     */
    public void setDate(final Date date) {
        this.date = (date != null) ? (new Date(date.getTime())) : (Date)null;
    }

    /**
     * Getter for property account.
     *
     * @return Value of property account
     */
    public Account getAccount() {
        return this.account;
    }

    /**
     * Setter for property account.
     *
     * @param account New value of property account.
     */
    public void setAccount(final Account account) {
        this.account = account;
    }

    /**
     * Determines if the time is billable.
     *
     * @return true if the time is billable otherwise false.
     */
    public boolean isBillable() {
        return account.isBillable();
    }

    /**
     * Getter for property hours.
     *
     * @return Value of property hours.
     */
    public int getHours() {
        return this.hours;
    }

    /**
     * Setter for property hours.
     *
     * @param hours New value of property hours must be > 0.
     *
     * @throws IllegalArgumentException if the hours are <=0.
     */
    public void setHours(final int hours) {
        // Make sure we have a legal hours value.
        if (hours <= 0) {
            throw new IllegalArgumentException(
                    "Hours must be a positive integer.");
        }
        // assert (hours > 0);
        this.hours = hours;
    }

    /**
     * Getter for property skill.
     *
     * @return Value of property skill.
     */
    public Skill getSkill() {
        return this.skill;
    }

    /**
     * {@inheritDoc}
     */
    //@Override
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((account == null) ? 0 : account.hashCode());
        result = prime * result + ((date == null) ? 0 : date.hashCode());
        result = prime * result + hours;
        result = prime * result + ((skill == null) ? 0 : skill.hashCode());
        return result;
    }

    /**
     * {@inheritDoc}
     */
    //@Override
    @Override
    public boolean equals(final Object obj) {
        boolean isEqual = false;
        if (this == obj) {
            isEqual = true;
        } else if (obj != null && getClass() == obj.getClass()) {
            final ConsultantTime other = (ConsultantTime) obj;
            if (account.equals(other.account) &&
                date.equals(other.date) &&
                skill.equals(other.skill) &&
                hours == other.hours) {
                isEqual = true;
            }
        }
        return isEqual;
    }

    /**
     * Creates a string representation of the consultant time.
     *
     * @return this ConsultantTime as a formatted string.
     */
    @Override
    public String toString() {
        return String.format("%-28s %2$tm/%2$td/%2$tY  %3$5d  %4$s%n",
                             account.getName(), date, hours, skill.getName());
    }
}
