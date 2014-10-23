package com.scg.domain;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.beans.PropertyVetoException;
import java.beans.VetoableChangeListener;
import java.beans.VetoableChangeSupport;
import java.io.Serializable;

import com.scg.util.Name;

/**
 * A consultant who is kept on staff (receives benefits).
 *
 * @author Russ Moul
 */
@SuppressWarnings("serial")
public final class StaffConsultant extends Consultant implements Serializable {

    /** Pay rate property name. */
    public static final String PAY_RATE_PROPERTY_NAME = "payRate";

    /** Vacation hours property name. */
    public static final String VACATION_HOURS_PROPERTY_NAME = "vacationHours";

    /** Pay rate property name. */
    public static final String SICK_LEAVE_HOURS_PROPERTY_NAME = "sickLeaveHours";

    /** Factor used in calculating hashCode. */
    private static final int HASH_FACTOR = 37;

    /** Pay rate in cents. */
    private int payRate;

    /** Sick leave hours balance. */
    private int sickLeaveHours;

    /** Vacation hours balance. */
    private int vacationHours;

    /** Property change support helper. */
    private final PropertyChangeSupport pcs;

    /** Vetoable change support helper. */
    private final VetoableChangeSupport vcs;

    /**
     * Creates a new instance of StaffConsultant
     *
     * @param name the consultant's name
     * @param rate the pay rate in cents
     * @param sickLeave the sick leave hours
     * @param vacation the vacation hours
     */
    public StaffConsultant(final Name name, final int rate,
                           final int sickLeave, final int vacation) {
        super(name);
        payRate = rate;
        sickLeaveHours = sickLeave;
        vacationHours = vacation;
        pcs = new PropertyChangeSupport(this);
        vcs = new VetoableChangeSupport(this);
    }

    /**
     * Get the current pay rate.
     *
     * @return the pay rate in cents
     */
    public int getPayRate() {
        return payRate;
    }

    /**
     * Set the pay rate. Fires the VetoableChange event.
     *
     * @param payRate the pay rate in cents
     * @throws PropertyVetoException if a veto occurs
     */
    public void setPayRate(final int payRate) throws PropertyVetoException {
        vcs.fireVetoableChange(PAY_RATE_PROPERTY_NAME, this.payRate, payRate);
        final int oldRate = this.payRate;
        this.payRate = payRate;
        pcs.firePropertyChange(PAY_RATE_PROPERTY_NAME, oldRate, payRate);
    }

    /**
     * Adds a general property change listener.
     *
     * @param l the listener
     */
    public void addPropertyChangeListener(final PropertyChangeListener l) {
        pcs.addPropertyChangeListener(l);
    }

    /**
     * Remove a general property change listener.
     *
     * @param l the listener
     */
    public void removePropertyChangeListener(final PropertyChangeListener l) {
        pcs.removePropertyChangeListener(l);
    }

    /**
     * Adds a payRate property change listener.
     *
     * @param l the listener
     */
    public void addPayRateListener(final PropertyChangeListener l) {
        pcs.addPropertyChangeListener(PAY_RATE_PROPERTY_NAME, l);
    }

    /**
     * Removes a payRate property change listener.
     *
     * @param l the listener
     */
    public void removePayRateListener(final PropertyChangeListener l) {
        pcs.removePropertyChangeListener(PAY_RATE_PROPERTY_NAME, l);
    }
    
    /**
     * Adds a vetoable change listener.
     *
     * @param l the listener
     */
    public void addVetoableChangeListener(final VetoableChangeListener l) {
        // The only constrained property is payRate register specifically for it
        vcs.addVetoableChangeListener(PAY_RATE_PROPERTY_NAME, l);
    }

    /**
     * Removes a vetoable change listener.
     *
     * @param l the listener
     */
    public void removeVetoableChangeListener(final VetoableChangeListener l) {
        vcs.removeVetoableChangeListener(PAY_RATE_PROPERTY_NAME, l);
    }


    /**
     * Get the available sick leave.
     *
     * @return the available sick leave hours
     */
    public int getSickLeaveHours() {
        return sickLeaveHours;
    }

    /**
     * Set the sick leave hours. Fires the ProperrtyChange event.
     *
     * @param sickLeaveHours the available sick leave hours
     */
    public void setSickLeaveHours(final int sickLeaveHours) {
        final int oldHours = this.sickLeaveHours;
        this.sickLeaveHours = sickLeaveHours;
        pcs.firePropertyChange(SICK_LEAVE_HOURS_PROPERTY_NAME, oldHours, sickLeaveHours);
    }

    /**
     * Adds a sickLeaveHours property change listener.
     *
     * @param l the listener
     */
    public void addSickLeaveHoursListener(final PropertyChangeListener l) {
        pcs.addPropertyChangeListener(SICK_LEAVE_HOURS_PROPERTY_NAME, l);
    }

    /**
     * Removes a sickLeaveHours property change listener.
     *
     * @param l the listener
     */
    public void removeSickLeaveHoursListener(final PropertyChangeListener l) {
        pcs.removePropertyChangeListener(SICK_LEAVE_HOURS_PROPERTY_NAME, l);
    }

    /**
     * Get the available vacation hours.
     *
     * @return the available vacation hours
     */
    public int getVacationHours() {
        return vacationHours;
    }

    /**
     * Set the vacation hours. Fires the ProperrtyChange event.
     *
     * @param vacationHours the vacation hours
     */
    public void setVacationHours(final int vacationHours) {
        final int oldHours = this.vacationHours;
        this.vacationHours = vacationHours;
        pcs.firePropertyChange(VACATION_HOURS_PROPERTY_NAME, oldHours, vacationHours);
    }

    /**
     * Adds a vacationHours property change listener.
     *
     * @param l the listener
     */
    public void addVacationHoursListener(final PropertyChangeListener l) {
        pcs.addPropertyChangeListener(VACATION_HOURS_PROPERTY_NAME, l);
    }

    /**
     * Removes a vacationHours property change listener.
     *
     * @param l the listener
     */
    public void removeVacationHoursListener(final PropertyChangeListener l) {
        pcs.removePropertyChangeListener(VACATION_HOURS_PROPERTY_NAME, l);
    }

    /**
     * Calculate the hash code.
     *
     * @return the hash code value
     */
    @Override
    public int hashCode() {
        int hc = StaffConsultant.class.hashCode();
        hc *= HASH_FACTOR + ((getName() == null) ? 0 : getName().hashCode());
        hc += hc * HASH_FACTOR + payRate;
        hc += hc * HASH_FACTOR + sickLeaveHours;
        hc += hc * HASH_FACTOR + vacationHours;

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
        if (other == null) {
            return false;
        }
        if (this.getClass() != other.getClass()) {
            return false;
        }
        final StaffConsultant o = (StaffConsultant)other;

        return getName().equals(o.getName()) &&
                payRate == o.payRate &&
                sickLeaveHours == o.sickLeaveHours &&
                vacationHours == o.vacationHours;
    }
}
