package com.scg.util;

import java.io.Serializable;
import java.util.Locale;

/**
 * A simple mailing address. Does no validity checking for things like valid
 * states or postal codes.  Instances of this class are immutable.
 *
 * @author Russ Moul
 */
public final class Address implements Serializable, Comparable<Address> {
    /** serialVersionUID */
    private static final long serialVersionUID = 7549265121331107862L;

    /** Factor used in calculating hashCode. */
    private static final int HASH_FACTOR = 37;

    /** Street number. */
    private final String streetNumber;

    /** City name. */
    private final String city;

    /** State or province. */
    private final StateCode state;

    /** Postal or zip code. */
    private final String postalCode;

    /** Hash code value. */
    private Integer hashCode;

    /**
     * Construct an Address.
     * @param streetNumber the street number.
     * @param city the city.
     * @param state the state.
     * @param postalCode the postal code.
     */
    public Address(final String streetNumber, final String city, final StateCode state, final String postalCode) {
        this.streetNumber = streetNumber;
        this.city = city;
        this.state = state;
        this.postalCode = postalCode;
    }

    /**
     * Get the street number number for this address.
     *
     * @return the street number.
     */
    public String getStreetNumber() {
        return streetNumber;
    }

    /**
     * Gets the city for this address.
     *
     * @return the city.
     */
    public String getCity() {
        return city;
    }

    /**
     * Get the state for this address.
     *
     * @return the state.
     */
    public StateCode getState() {
        return state;
    }

    /**
     * Gets the postal code for this address.
     *
     * @return the postal code.
     */
    public String getPostalCode() {
        return postalCode;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        if (hashCode == null) {
            int result = Address.class.hashCode();
            result = HASH_FACTOR * result + ((city == null) ? 0 : city.hashCode());
            result = HASH_FACTOR * result + ((postalCode == null) ? 0 : postalCode.hashCode());
            result = HASH_FACTOR * result + ((state == null) ? 0 : state.hashCode());
            result = HASH_FACTOR * result + ((streetNumber == null) ? 0 : streetNumber.hashCode());
            hashCode = result;
        }
        return hashCode;
    }

    /**
     * Compares two Address object for value equality.
     *
     * @param obj the object to compare to the object
     *
     * @return true if all fields are equal
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
        final Address other = (Address) obj;
        if (city == null) {
            if (other.city != null) {
                return false;
            }
        } else if (!city.equals(other.city)) {
            return false;
        }
        if (postalCode == null) {
            if (other.postalCode != null) {
                return false;
            }
        } else if (!postalCode.equals(other.postalCode)) {
            return false;
        }
        if (state == null) {
            if (other.state != null) {
                return false;
            }
        } else if (!state.equals(other.state)) {
            return false;
        }
        if (streetNumber == null) {
            if (other.streetNumber != null) {
                return false;
            }
        } else if (!streetNumber.equals(other.streetNumber)) {
            return false;
        }
        return true;
    }

    /**
     * Prints this address in the form:
     * <br>
     * <br> street number
     * <br> city, state postal code
     *
     * @return the formatted address.
     */
    @Override
    public String toString() {
        return String.format(Locale.US, "%s%n%s, %s %s", streetNumber, city,
                             state, postalCode);
    }

    /**
     * Compares TimeCard, by ascending order of state, postalCode, city,
     * and streetNumber.
     *
     * @param other the TimeCard to compare to
     *
     * @return a negative integer, zero, or a positive integer as this object is
     *         less than, equal to, or greater than the specified object.
     */

    @Override
    public int compareTo(final Address other) {
        final int EQUAL = 0;
        int diff;
        diff = state.compareTo(other.state);
        if (diff != EQUAL) return diff;
        
        diff = postalCode.compareTo(other.postalCode);
        if (diff != EQUAL) return diff;

        diff = city.compareTo(other.city);
        if (diff != EQUAL) return diff;

        return streetNumber.compareTo(other.streetNumber);
    }

}
