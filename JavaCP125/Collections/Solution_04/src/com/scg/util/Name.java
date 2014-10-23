package com.scg.util;

/**
 * Encapsulates the first, middle and last name of a person.
 *
 * @author Russ Moul
 */
public final class Name implements Comparable<Name> {
    /** Factor used in calculating hashCode. */
    private static final int HASH_FACTOR = 37;
    
    /** String constant for "NLN". */
    private static final String NLN = "NLN";
    /** String constant for "NFN". */
    private static final String NFN = "NFN";
    /** String constant for "NMN". */
    private static final String NMN = "NMN";

    /**
     * The last name.
     */
    private String lastName;

    /**
     * The middle name.
     */
    private String middleName;

    /**
     * The firstName.
     */
    private String firstName;

    /** Creates a new instance of Name */
    public Name() {
        this.lastName = NLN;
        this.firstName = NFN;
        this.middleName = NMN;
    }

    /**
     * Construct a Name.
     *
     * @param lastName Value for the last name.
     * @param firstName Value for the first name.
     * @param middleName Value for the middle name.
     */
    public Name(final String lastName, final String firstName, final String middleName) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.middleName = middleName;
    }

    /**
     * Construct a Name.
     *
     * @param lastName Value for the last name.
     * @param firstName Value for the first name.
     */
    public Name(final String lastName, final String firstName) {
        this(lastName, firstName, NMN);
    }

    /**
     * Getter for property firstName.
     *
     * @return Value of property firstName.
     */
    public String getFirstName() {
        return this.firstName;
    }

    /**
     * Setter for property first.
     *
     * @param firstName New value of property firstName.
     */
    public void setFirstName(final String firstName) {
        this.firstName = firstName;
    }

    /**
     * Getter for property lastName.
     *
     * @return Value of property lastName.
     */
    public String getLastName() {
        return this.lastName;
    }

    /**
     * Setter for property lastName.
     *
     * @param lastName New value of property lastName.
     */
    public void setLastName(final String lastName) {
        this.lastName = lastName;
    }

    /**
     * Getter for property middleName.
     *
     * @return Value of property middleName.
     */
    public String getMiddleName() {
        return this.middleName;
    }

    /**
     * Setter for property middleName.
     *
     * @param middleName New value of property middleName.
     */
    public void setMiddleName(final String middleName) {
        this.middleName = middleName;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        int hc = Name.class.hashCode();
        hc = hc * HASH_FACTOR + ((lastName == null) ? 0 : lastName.hashCode());
        hc = hc * HASH_FACTOR + ((firstName == null) ? 0 : firstName.hashCode());
        hc = hc * HASH_FACTOR + ((middleName == null) ? 0 : middleName.hashCode());
        return hc;
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
        if (!(other instanceof Name)) {
            return false;
        }
        final Name o = (Name)other;

        return ((lastName == null && o.lastName == null) ||
                ((lastName != null && o.lastName != null) &&
                  lastName.equals(o.lastName))) &&
               ((firstName == null && o.firstName == null) ||
                ((firstName != null && o.firstName != null) &&
                  firstName.equals(o.firstName))) &&
               ((middleName == null && o.middleName == null) ||
                ((middleName != null && o.middleName != null) &&
                  middleName.equals(o.middleName)));
    }

    /**
     * Create string representation of this object in the format
     * <br>
     * "LastName, FirstName MiddleName".
     *
     * @return the formatted name.
     */
    @Override
    public String toString() {
        final StringBuilder output = new StringBuilder();
        output.append(lastName);
        output.append(", ");
        output.append(firstName);
        output.append(" ");
        output.append(middleName);
        return output.toString();
    }

    /**
     * Compares this object with the specified object for order. Returns a
     * negative integer, zero, or a positive integer as this object is less
     * than, equal to, or greater than the specified object.
     * <p>
     *
     * @param other the Object to be compared.
     * @return a negative integer, zero, or a positive integer as this object is
     *         less than, equal to, or greater than the specified object.
     */
    @Override
    public int compareTo(final Name other) {
        int diff;
        diff = (lastName == null)
             ? ((other.lastName == null) ? 0 : 1)
             : lastName.compareTo(other.lastName);

        if (diff == 0) {
            diff = (firstName == null)
                 ? ((other.firstName == null) ? 0 : 1)
                 : firstName.compareTo(other.firstName);
            if (diff == 0) {
                diff = (middleName == null)
                     ? ((other.middleName == null) ? 0 : 1)
                     : middleName.compareTo(other.middleName);
            }
        }

        return diff;
    }
}
