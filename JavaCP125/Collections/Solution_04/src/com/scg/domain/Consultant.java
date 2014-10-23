package com.scg.domain;

import com.scg.util.Name;

/**
 * A consultant.
 *
 * @author Russ Moul
 */
public class Consultant implements Comparable<Consultant> {
    /** Factor used in calculating hashCode. */
    private static final int HASH_FACTOR = 37;

    /** Holds value of property name. */
    private Name name;

    /** The hash code value. */
    private int hashCode;

    /**
     * Creates a new instance of AbstractConsultant.
     *
     * @param name the consultant's name.
     */
    public Consultant(final Name name) {
        this.name = name;

        this.hashCode = calcHashCode();
    }

    /**
     * Getter for property name.
     *
     * @return Value of property name.
     *
     */
    public final Name getName() {
        return this.name;
    }

    /**
     * Returns the string representation of the consultant's name.
     *
     * @return the consultant name string
     *
     */
    @Override
    public final String toString() {
        return name.toString();
    }

    /**
     * Calculate the hash code.
     *
     * @return the hash code value
     */
    private int calcHashCode() {
        int hc = Consultant.class.hashCode();
        hc *= HASH_FACTOR + ((name == null) ? 0 : name.hashCode());
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
     * Compare names for equivalence.
     *
     * @param other the name to compare to
     *
     * @return true if all the name elements have the same value
     */
    @Override
    public boolean equals(final Object other) {
        if (other == null) {
            return false;
        }
        if (this.getClass() != other.getClass()) {
            return false;
        }
        final Consultant o = (Consultant)other;

        return (name == null && o.name == null) || 
               ((name != null && o.name != null) && name.equals(o.name));
    }

    /**
     * Compares this Consultant object with the specified object for order. Returns a
     * negative integer, zero, or a positive integer as this object is less
     * than, equal to, or greater than the specified object, the consultant name
     * is used to perform the comparison.
     * <p>
     *
     * @param other the Object to be compared.
     * @return a negative integer, zero, or a positive integer as this object is
     *         less than, equal to, or greater than the specified object.
     */
    @Override
    public final int compareTo(final Consultant other) {
        return name.compareTo(other.name);
    }
}
