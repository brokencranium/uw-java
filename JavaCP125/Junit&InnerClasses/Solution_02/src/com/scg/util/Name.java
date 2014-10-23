package com.scg.util;

/**
 * Encapsulates the first, middle and last name of a person.
 *
 * @author Russ Moul
 */
public final class Name {
    /** String constant "NFN". */
    private static final String NFN = "NFN";
    /** String constant "NLN". */
    private static final String NLN = "NLN";
    /** String constant "NMN". */
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
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((firstName == null) ? 0 : firstName.hashCode());
        result = prime * result
                + ((lastName == null) ? 0 : lastName.hashCode());
        result = prime * result
                + ((middleName == null) ? 0 : middleName.hashCode());
        return result;
    }
    

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(final Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;

        final Name other = (Name) obj;
        if (firstName == null) {
            if (other.firstName != null) {
                return false;
            }
        } else if (!firstName.equals(other.firstName)) {
            return false;
        }

        if (lastName == null) {
            if (other.lastName != null) {
                return false;
            }
        } else if (!lastName.equals(other.lastName)) {
            return false;
        }

        if (middleName == null) {
            if (other.middleName != null) {
                return false;
            }
        } else if (!middleName.equals(other.middleName)) {
            return false;
        }
        
        return true;
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
}
