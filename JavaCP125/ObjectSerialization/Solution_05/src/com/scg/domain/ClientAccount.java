package com.scg.domain;

import java.io.Serializable;

import com.scg.util.Address;
import com.scg.util.Name;

/**
 * Encapsulates the information for a billable client account.
 *
 * @author Russ Moul
 */
public final class ClientAccount implements Account, Comparable<ClientAccount>, Serializable {

	/** Serial version id. */
    private static final long serialVersionUID = -7290075852886590617L;

    /** Factor used in calculating hashCode. */
    private static final int HASH_FACTOR = 37;

    /**
     * Holds value of property name.
     */
    private final String name;

    /**
     * Holds value of property contact.
     */
    private final Name contact;

    /**
     * Address of this Client.
     */
    private final Address address;

    /** Hash code value. */
    private final int hashCode;

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

        this.hashCode = calcHashCode();
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
     * Getter for property address.
     *
     * @return value of property address.
     */
    public Address getAddress() {
        return this.address;
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

    /**
     * Compares this Client with the specified Client for order, ordering by
     * name, contact, and finally address. Returns a negative integer, zero, or
     * a positive integer as this Client is less than, equal to, or greater than
     * the specified Client.
     * <p>
     *
     * @param other
     *            the Client to be compared.
     * @return a negative integer, zero, or a positive integer as this Client is
     *         less than, equal to, or greater than the specified Client.
     */
    @Override
    public int compareTo(final ClientAccount other) {
        final int EQUAL = 0;
        if ( this == other ) return EQUAL;


        int diff = name.compareTo(other.name);
        if (diff == EQUAL) return diff;
        
        diff = contact.compareTo(other.contact);
        if (diff == EQUAL) return diff;
        
        return address.compareTo(other.address);
    }

    /**
     * Calculate the hash code.
     *
     * @return the hash code value
     */
    private int calcHashCode() {
        int hc = ClientAccount.class.hashCode();

        hc *= HASH_FACTOR + ((name == null) ? 0 : name.hashCode());
        hc *= HASH_FACTOR + ((contact == null) ? 0 : contact.hashCode());
        hc *= HASH_FACTOR + ((address == null) ? 0 : address.hashCode());

        return hc;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return hashCode;
    }

    /**
     * Indicates whether some other object is "equal to" this one.
     *
     * @param other the reference to other object with which to compare.
     * @return <code>true</code> if ClientAccount has the same name as the
     *         the other object argument; <code>false</code> otherwise.
     */
    @Override
    public boolean equals(final Object other) {
        if (!(other instanceof ClientAccount)) {
            return false;
        }

        return compareTo((ClientAccount)other) == 0;
    }

}
