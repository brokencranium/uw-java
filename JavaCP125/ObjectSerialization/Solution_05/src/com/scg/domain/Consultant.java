package com.scg.domain;

import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.logging.Logger;

import com.scg.util.Name;

/**
 * A consultant.
 *
 * @author Russ Moul
 */
public class Consultant implements Comparable<Consultant>, Serializable {

    /** Serial version id. */
	private static final long serialVersionUID = 3567075357733206298L;

	/** Factor used in calculating hashCode. */
    private static final int HASH_FACTOR = 37;

    /** This class' logger. */
    static final Logger log = Logger.getLogger(Consultant.class.getName());

    /** Holds value of property name. */
    private final Name name;

    /** The hash code value. */
    private final int hashCode;

    /**
     * Serialization proxy for the Consultant class
     */
    private static final class SerializationProxy implements Serializable {
        /** Serial version id. */
		private static final long serialVersionUID = -3820527788271170781L;
		/** Variable for last name. */
        private final String x;
        /** Variable for first name. */
        private final String y;
        /** Variable for middle name. */
        private final String z;

        /**
         * Constructor.
         *
         * @param consultant consultant to proxy
         */
        SerializationProxy(final Consultant consultant) {
            final Name name = consultant.getName();
            x = name.getLastName();
            y = name.getFirstName();
            z = name.getMiddleName();
        }

        /**
         * Create and returns the proxied Consultant.
         *
         * @return a new instance of the proxied Consultant
         */
        private Object readResolve() {
            final String msg = String.format("De-serialized consultant: %s, %s %s", x, y, z);
            log.info(msg);
            return new Consultant(new Name(x, y, z));
        }
    }

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

        return (name == null && o.name == null) || ((name != null && o.name != null) && name.equals(o.name));
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

    /**
     * Writes this object's proxy to stream.
     *
     * @return the serialization proxy
     */
    private Object writeReplace() {
        final String msg = String.format("Serializing consultant: %s", getName());
        log.info(msg);
        return new SerializationProxy(this);
    }

    /**
     * Throws an InvalidObjectException a serialization proxy must be used
     *
     * @param ois not used
     * @throws InvalidObjectException always
     */
    private void readObject(final ObjectInputStream ois)
        throws InvalidObjectException {
        throw new InvalidObjectException("Proxy required");
    }
}
