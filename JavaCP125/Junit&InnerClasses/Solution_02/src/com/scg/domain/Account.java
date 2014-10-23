package com.scg.domain;

/**
 * Interface all accounts must implement.
 * @author  Russ Moul
 */
public interface Account {
    /**
     * Getter for the name of this account.
     *
     * @return the name of this account.
     */
    String getName();

    /**
     * Determines if this account is billable.
     *
     * @return true if the account is billable otherwise false.
     *
     */
    boolean isBillable();
}
