package com.scg.domain;

import com.scg.util.Address;
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
    private String name;

    /**
     * Holds value of property contact.
     */
    private Name contact;

    /**
     * Address of this Client.
     */
    private Address address;

    /**
     * Creates a new instance of ClientAccount.
     *
     * @param name String with the name of the client.
     * @param contact Name of the contact person for this client.
     * @param address Address of this client.
     */
    public ClientAccount(final String name, final Name contact, final Address address) {
        this.name = name;
        this.contact = contact;
        this.address = address;
    }

    /**
     * Gets the account name.
     *
     * @return Value of property name.
     */
    @Override
    public String getName() {
        return this.name;
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
     * Getter for property address.
     *
     * @return value of property address.
     */
    public Address getAddress() {
        return this.address;
    }

    /**
     * Setter for property address.
     *
     * @param address New value of property address.
     */
    public void setAddress(final Address address) {
        this.address = address;
    }

    /**
     * String representation for this Client.
     *
     * @return Formatted string of the form
     *
     * <pre>
     *  Client Name
     *  Client Address
     *  Client Contact Name
     * </pre>
     */

    @Override
    public String toString() {
        return String.format("%s%n%s%n%s%n", name, address.toString(), contact.toString());
    }

}
