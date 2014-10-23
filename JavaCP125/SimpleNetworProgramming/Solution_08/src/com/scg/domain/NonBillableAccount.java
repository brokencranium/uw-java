package com.scg.domain;

/**
 * Encapsulates the concept of a non-billable account, such as sick leave,
 * vacation, or business development.
 *
 * @author Russ Moul
 */
public enum NonBillableAccount implements Account {
    /** Sick Leave. */
    SICK_LEAVE("Sick Leave"),
    /** Vacation. */
    VACATION("Vacation"),
    /** Business development. */
    BUSINESS_DEVELOPMENT("Business Development");


    /** The name of this account. */
    private final String name;

    /**
     * Creates a new instance of NonBillableAccount
     *
     * @param name
     *            the name of this account.
     */
    private NonBillableAccount(final String name) {
        this.name = name;
    }

    /**
     * Getter for the name of this account.
     *
     * @return the name of this account.
     */
    @Override
    public String getName() {
        return this.name;
    }

    /**
     * Determines if this account is billable.
     *
     * @return always false
     *
     */
    @Override
    public boolean isBillable() {
        return false;
    }
}
