package com.scg.domain;

import com.scg.util.Name;

/**
 * Encapsulates the information for a billable client account.
 *
 * @author Russ Moul
 */
public final class ClientAccount implements Account {
    /**
     * Holds value of property name.
     */
    private final String name;

    /**
     * Holds value of property contact.
     */
    private Name contact;

    /**
     * Creates a new instance of ClientAccount.
     *
     * @param name String with the name of the client.
     * @param contact Name of the contact person for this client.
     */
    public ClientAccount(final String name, final Name contact) {
        this.name = name;
        this.contact = contact;
    }

    /**
     * Gets the account name.
     *
     * @return Value of property name.
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * Gets the contact for this account.
     *
     * @return Value of property contact.
     */
    public Name getContact() {
        return this.contact;
    }

    /**
     * Setter for property contact.
     *
     * @param contact New value of property contact.
     */
    public void setContact(final Name contact) {
        this.contact = contact;
    }

    /**
     * Determines if this account is billable.
     *
     * @return always true
     */
    @Override
    public boolean isBillable() {
        return true;
    }
}
